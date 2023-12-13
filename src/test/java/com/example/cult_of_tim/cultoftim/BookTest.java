package com.example.cult_of_tim.cultoftim;

import com.example.cult_of_tim.cultoftim.converter.AuthorConverter;
import com.example.cult_of_tim.cultoftim.converter.BookConverter;
import com.example.cult_of_tim.cultoftim.converter.CategoryConverter;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.repositories.AuthorRepository;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.impl.AuthorServiceImpl;
import com.example.cult_of_tim.cultoftim.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {AuthorServiceImpl.class, AuthorRepository.class, AuthorConverter.class, BookServiceImpl.class, BookRepository.class, BookConverter.class, CategoryConverter.class})
public class BookTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book1", 20, Collections.emptyList(), Collections.emptyList(), "1234567890123", 5, true));
        books.add(new Book(2L, "Book2", 30, Collections.emptyList(), Collections.emptyList(), "1234567890456", 3, false));
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> result = bookService.getAllBooks();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Book1", result.get(0).getTitle());
        Assertions.assertEquals("Book2", result.get(1).getTitle());
    }

    @Test
    void testCreateBook() {
        BookDto creationBookDto = new BookDto();
        creationBookDto.setTitle("New Book");
        creationBookDto.setAuthors(Collections.emptyList());
        creationBookDto.setCategories(Collections.emptyList());

        Mockito.when(bookRepository.save(Mockito.any())).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(1L);
            return savedBook;
        });

        BookDto result = bookService.createBook(creationBookDto);

        Assertions.assertEquals(creationBookDto.getTitle(), result.getTitle());
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setId(bookId);
        updatedBookDto.setTitle("Updated Book");
        updatedBookDto.setAuthors(Collections.emptyList());
        updatedBookDto.setCategories(Collections.emptyList());

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Old Book");

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(existingBook);

        BookDto result = bookService.updateBook(bookId, updatedBookDto);

        Assertions.assertEquals(updatedBookDto.getId(), result.getId());
        Assertions.assertEquals(updatedBookDto.getTitle(), result.getTitle());
    }

    @Test
    void testGetBooksByAuthorId() {
        Long authorId = 1L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book1", 20, Collections.emptyList(), Collections.emptyList(), "1234567890123", 5, true));
        books.add(new Book(2L, "Book2", 30, Collections.emptyList(), Collections.emptyList(), "1234567890456", 3, false));

        Mockito.when(bookRepository.findByAuthorsId(authorId)).thenReturn(books);

        List<BookDto> result = bookService.getBooksByAuthorId(authorId);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Book1", result.get(0).getTitle());
        Assertions.assertEquals("Book2", result.get(1).getTitle());
    }

    @Test
    void testGetBooksByCategoryId() {
        Long categoryId = 1L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book1", 20, Collections.emptyList(), Collections.emptyList(), "1234567890123", 5, true));
        books.add(new Book(2L, "Book2", 30, Collections.emptyList(), Collections.emptyList(), "1234567890456", 3, false));

        Mockito.when(bookRepository.findByCategoriesId(categoryId)).thenReturn(books);

        List<BookDto> result = bookService.getBooksByCategoryId(categoryId);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Book1", result.get(0).getTitle());
        Assertions.assertEquals("Book2", result.get(1).getTitle());
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        bookService.deleteBook(bookId);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }

    @Test
    void testBookExists() {
        String title = "Book1";
        Mockito.when(bookRepository.findByTitle(title)).thenReturn(Collections.singletonList(new Book()));
        boolean result = bookService.bookExists(title);
        Assertions.assertTrue(result);
    }
}