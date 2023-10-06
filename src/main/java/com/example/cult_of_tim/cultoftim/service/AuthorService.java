package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<AuthorDto> getAuthorById(Long id);

    List<AuthorDto> getAllAuthors();

    AuthorDto createAuthor(String firstName, String lastName);

    AuthorDto updateAuthor(Long id, AuthorDto updatedAuthor);

    void deleteAuthor(Long id);
}
