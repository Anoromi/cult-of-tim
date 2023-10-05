package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.Author;
import com.example.cult_of_tim.cultoftim.models.Book;
import com.example.cult_of_tim.cultoftim.models.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(Long id);

    List<Book> getAllBooks();


    List<Book> getBooksByAuthorId(Long authorId);

    List<Book> getBooksByCategoryId(Long categoryId);

    Book createBook(String title, List<Author> author, List<Category> category);

    Book updateBook(Long id, Book updatedBook);

    void deleteBook(Long id);
}
