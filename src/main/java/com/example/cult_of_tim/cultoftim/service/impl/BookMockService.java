package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.dao.BookDao;
import com.example.cult_of_tim.cultoftim.models.Book;
import com.example.cult_of_tim.cultoftim.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookMockService implements BookService {

    @Autowired
    private BookDao bookDao;

    // better
    /*
    @Autowired
    public BookMockService(BookDao bookDao) {
        this.bookDao = bookDao;
    }*/

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookDao.getBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookDao.getBooksByAuthorId(authorId);
    }

    @Override
    public List<Book> getBooksByCategoryId(Long categoryId) {
        return bookDao.getBooksByCategoryId(categoryId);
    }

    @Override
    public Long createBook(String title, List<Long> authorIds, List<Long> categoryIds) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthorIDs(authorIds);
        newBook.setCategoryIDs(categoryIds);
        return bookDao.createBook(newBook);
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {
        updatedBook.setId(id);
        return bookDao.updateBook(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.deleteBookById(id);
    }

}
