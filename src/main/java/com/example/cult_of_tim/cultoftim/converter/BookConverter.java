package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.controller.request.BookRequest;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookConverter {

    private AuthorConverter authorConverter;
    private CategoryConverter categoryConverter;

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(book.getAuthors().stream().map(authorConverter::toDto).collect(Collectors.toList()))
                .categories(book.getCategories().stream().map(categoryConverter::toDto).collect(Collectors.toList()))
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .build();
    }

    public BookDto toDto(BookRequest book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(book.getAuthors().stream().map(authorConverter::requestToDto).collect(Collectors.toList()))
                .categories(book.getCategories().stream().map(categoryConverter::toDto).collect(Collectors.toList()))
                .price(book.getPrice())
                .quantity(book.getQuantity())
                .build();
    }

    public Book toEntity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .authors(bookDto.getAuthors().stream().map(authorConverter::toEntity).collect(Collectors.toList()))
                .categories(bookDto.getCategories().stream().map(categoryConverter::toEntity).collect(Collectors.toList()))
                .price(bookDto.getPrice())
                .quantity(bookDto.getQuantity())
                .build();
    }
}

