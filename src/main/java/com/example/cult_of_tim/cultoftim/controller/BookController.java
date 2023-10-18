package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (bookRepository.existsById(id)) {
            updatedBook.setId(id);
            Book savedBook = bookRepository.save(updatedBook);
            return ResponseEntity.ok(savedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byAuthor/{authorId}")
    public List<Book> getBooksByAuthorId(@PathVariable Long authorId) {
        return bookRepository.findByAuthorsId(authorId);
    }

    @GetMapping("/byCategory/{categoryId}")
    public List<Book> getBooksByCategoryId(@PathVariable Long categoryId) {
        return bookRepository.findByCategoriesId(categoryId);
    }

    @GetMapping("/available/{available}")
    public List<Book> getBooksByAvailable(@PathVariable boolean available) {
        return bookRepository.findByAvailable(available);
    }
}
