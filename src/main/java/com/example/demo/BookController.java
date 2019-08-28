package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Book> find(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        if (book != null) {
            return new ResponseEntity(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> list() {
        return new ResponseEntity(bookRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book){
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

//    @PostMapping
//    public String save(@RequestBody Book book){
//        bookRepository.save(book);
//        return "Book "+book+" added";
//    }

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
