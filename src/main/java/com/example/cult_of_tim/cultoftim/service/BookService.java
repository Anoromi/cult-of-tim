package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDto> getBookById(Long id);

    List<BookDto> getAllBooks();


    List<BookDto> getBooksByAuthorId(Long authorId);

    List<BookDto> getBooksByCategoryId(Long categoryId);

    BookDto createBook(BookDto creationBook);

    BookDto updateBook(Long id, BookDto updatedBook);

    void deleteBook(Long id);
}
