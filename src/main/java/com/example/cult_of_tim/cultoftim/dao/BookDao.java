package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBooksByAuthorId(Long authorId);

    List<Book> getBooksByCategoryId(Long categoryId);

    Long createBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
