package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {
    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public Author toEntity(AuthorDto authorDto) {
        return Author.builder()
                .id(authorDto.getId())
                .firstName(authorDto.getFirstName())
                .lastName(authorDto.getLastName())
                .build();
    }
}
