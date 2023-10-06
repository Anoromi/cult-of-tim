package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.converter.BookConverter;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    @Override
    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id).map(bookConverter::toDto);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByAuthorId(Long authorId) {
        List<Book> books = bookRepository.findByAuthorsId(authorId);
        return books.stream()
                .map(bookConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByCategoryId(Long categoryId) {
        List<Book> books = bookRepository.findByCategoriesId(categoryId);
        return books.stream()
                .map(bookConverter::toDto)
                .collect(Collectors.toList());
    }

    //TODO creation dto
    @Override
    public BookDto createBook(BookDto bookDto) {
        Book newBook = new Book();
        Book creationBook = bookConverter.toEntity(bookDto);
        newBook.setTitle(bookDto.getTitle());
        newBook.setAuthors(creationBook.getAuthors());
        newBook.setCategories(creationBook.getCategories());
        Book savedBook = bookRepository.save(newBook);
        return bookConverter.toDto(savedBook);
    }

    @Override
    public BookDto updateBook(Long id, BookDto updatedBookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Book updatedBook = bookConverter.toEntity(updatedBookDto);
        updatedBook.setId(id);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthors(updatedBook.getAuthors());
        existingBook.setCategories(updatedBook.getCategories());

        Book savedBook = bookRepository.save(existingBook);
        return bookConverter.toDto(savedBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
