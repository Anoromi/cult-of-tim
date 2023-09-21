package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(Long id);

    List<Book> getAllBooks();

    List<Book> getBooksByAuthorId(Long authorId);

    List<Book> getBooksByCategoryId(Long categoryId);

    Long createBook(String title, List<Long> authorIds, List<Long> categoryIds);

    void updateBook(Long id, Book updatedBook);

    void deleteBook(Long id);
}
