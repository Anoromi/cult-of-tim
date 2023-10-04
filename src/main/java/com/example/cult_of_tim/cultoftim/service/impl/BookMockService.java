package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.dao.BookDao;
import com.example.cult_of_tim.cultoftim.models.Author;
import com.example.cult_of_tim.cultoftim.models.Book;
import com.example.cult_of_tim.cultoftim.models.Category;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookMockService implements BookService {

    @Autowired
    private BookRepository bookRepository;

    // better
    /*
    @Autowired
    public BookMockService(BookDao bookDao) {
        this.bookDao = bookDao;
    }*/

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @Override
    public List<Book> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId);
    }

    @Override
    public Book createBook(String title, List<Author> authors, List<Category> category) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthors(authors);
        newBook.setCategories(category);
        return bookRepository.save(newBook);
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {
        updatedBook.setId(id);
        return bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}
