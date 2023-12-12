package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.controller.request.AuthorRequest;
import com.example.cult_of_tim.cultoftim.controller.request.BookRequest;
import com.example.cult_of_tim.cultoftim.controller.request.CategoryRequest;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.dto.CategoryDto;
import com.example.cult_of_tim.cultoftim.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookRequest> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return books.stream()
                .map(this::mapToBookRequest)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookRequest> getBookById(
            @PathVariable Long id) {
        Optional<BookDto> book = bookService.getBookById(id);
        if (book.isPresent()) {
            BookRequest response = mapToBookRequest(book.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BookRequest.class))
    })

    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody())
    public ResponseEntity<BookRequest> createBook(
            @RequestBody
            BookRequest bookRequest) {
        BookDto bookDto = mapToBookDto(bookRequest);
        BookDto createdBook = bookService.createBook(bookDto);
        BookRequest response = mapToBookRequest(createdBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody())
    public ResponseEntity<BookRequest> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        Optional<BookDto> existingBook = bookService.getBookById(id);
        if (existingBook.isPresent()) {
            BookDto updatedBook = bookService.updateBook(id, mapToBookDto(bookRequest));
            BookRequest response = mapToBookRequest(updatedBook);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<BookDto> existingBook = bookService.getBookById(id);
        if (existingBook.isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byAuthor/{authorId}")
    public List<BookRequest> getBooksByAuthorId(@PathVariable Long authorId) {
        List<BookDto> books = bookService.getBooksByAuthorId(authorId);
        return books.stream()
                .map(this::mapToBookRequest)
                .collect(Collectors.toList());
    }

    @GetMapping("/byCategory/{categoryId}")
    public List<BookRequest> getBooksByCategoryId(@PathVariable Long categoryId) {
        List<BookDto> books = bookService.getBooksByCategoryId(categoryId);
        return books.stream()
                .map(this::mapToBookRequest)
                .collect(Collectors.toList());
    }

    private BookRequest mapToBookRequest(BookDto bookDto) {
        BookRequest bookRequest = BookRequest.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .quantity(bookDto.getQuantity())
                .isbn13(bookDto.getIsbn13())
                .build();

        List<AuthorRequest> authors = bookDto.getAuthors().stream()
                .map(this::mapToAuthorRequest)
                .collect(Collectors.toList());
        bookRequest.setAuthors(authors);

        List<CategoryRequest> categories = bookDto.getCategories().stream()
                .map(this::mapToCategoryRequest)
                .collect(Collectors.toList());
        bookRequest.setCategories(categories);

        return bookRequest;
    }

    private BookDto mapToBookDto(BookRequest bookRequest) {
        BookDto bookDto = BookDto.builder()
                .id(bookRequest.getId())
                .title(bookRequest.getTitle())
                .quantity(bookRequest.getQuantity())
                .isbn13(bookRequest.getIsbn13())
                .build();

        List<AuthorDto> authors = bookRequest.getAuthors().stream()
                .map(this::mapToAuthorDto)
                .collect(Collectors.toList());
        bookDto.setAuthors(authors);

        List<CategoryDto> categories = bookRequest.getCategories().stream()
                .map(this::mapToCategoryDto)
                .collect(Collectors.toList());
        bookDto.setCategories(categories);

        return bookDto;
    }

    private AuthorDto mapToAuthorDto(AuthorRequest authorRequest) {
        return AuthorDto.builder()
                .id(authorRequest.getId())
                .fullName(authorRequest.getFullName())
                .build();
    }

    private CategoryDto mapToCategoryDto(CategoryRequest categoryRequest) {
        return CategoryDto.builder()
                .id(categoryRequest.getId())
                .name(categoryRequest.getName())
                .build();
    }

    private AuthorRequest mapToAuthorRequest(AuthorDto authorDto) {
        return AuthorRequest.builder()
                .id(authorDto.getId())
                .fullName(authorDto.getFullName())
                .build();
    }

    private CategoryRequest mapToCategoryRequest(CategoryDto categoryDto) {
        return CategoryRequest.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
