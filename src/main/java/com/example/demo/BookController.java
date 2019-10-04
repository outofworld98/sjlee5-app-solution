package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController
{
    private final BookRepository bookRepository;
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> find(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return new ResponseEntity<Object>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> list() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book){

        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        Book bookEntity = bookRepository.findById(id).get();
        if (book != null) {
            bookEntity.setAuthor(book.getAuthor());
            bookEntity.setPrice(book.getPrice());
            bookEntity.setTitle(book.getTitle());

            Book updatedBook = bookRepository.save(bookEntity);
            return new ResponseEntity<>(bookEntity, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
