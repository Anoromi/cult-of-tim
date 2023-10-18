package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.converter.AuthorConverter;
import com.example.cult_of_tim.cultoftim.converter.BookConverter;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.external.OpenLibraryBook;
import com.example.cult_of_tim.cultoftim.repositories.AuthorRepository;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import com.example.cult_of_tim.cultoftim.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private AuthorService authorService;
    private AuthorConverter authorConverter;

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
    public void addBookFromOpenLibrary(String isbn13, int quantity) {

        try {
            var result = WebClient.builder()
                    .baseUrl("https://openlibrary.org")
                    .clientConnector(new ReactorClientHttpConnector(
                            HttpClient.create().followRedirect(true)
                    ))
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build().get().uri("/isbn/{isbn}.json", isbn13)

                    .retrieve()
                    .toEntity(OpenLibraryBook.class)
                    .block();

            assert result != null;
            assert result.getBody() != null;
            var bodyBook = result.getBody();

            var addedAuthors = bodyBook.authors().stream().map(
                    v -> authorConverter.toEntity(authorService.createAuthorFromOpenLibrary(v.key().replace("/authors/", "")))
            ).toList();

            var book = new Book();
            book.setTitle(bodyBook.title());
            book.setIsbn13(isbn13);
            book.setAuthors(addedAuthors);

            book.setQuantity(quantity);

            bookRepository.save(book);



        } catch (WebClientResponseException e) {
            if (e.getStatusCode().equals(HttpStatusCode.valueOf(404)))
                throw new IllegalArgumentException("Isbn13 book not found");
        }



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

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setAuthorConverter(AuthorConverter authorConverter) {
        this.authorConverter = authorConverter;
    }
}
