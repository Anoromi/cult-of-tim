package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookConverter {

    private AuthorConverter authorConverter;
    private CategoryConverter categoryConverter;

    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthors(book.getAuthors().stream().map(authorConverter::toDto).collect(Collectors.toList()));
        bookDto.setCategories(book.getCategories().stream().map(categoryConverter::toDto).collect(Collectors.toList()));
        bookDto.setQuantity(book.getQuantity());
        bookDto.setAvailable(book.isAvailable());
        return bookDto;
    }

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthors(bookDto.getAuthors().stream().map(authorConverter::toEntity).collect(Collectors.toList()));
        book.setCategories(bookDto.getCategories().stream().map(categoryConverter::toEntity).collect(Collectors.toList()));
        book.setQuantity(bookDto.getQuantity());
        book.setAvailable(bookDto.isAvailable());
        return book;
    }
}

