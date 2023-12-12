package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<AuthorDto> getAuthorById(Long id);

    List<AuthorDto> getAllAuthors();

    AuthorDto createAuthor(String fullName);

    AuthorDto createAuthorFromOpenLibrary(String openLibrary);

    AuthorDto updateAuthor(Long id, AuthorDto updatedAuthor);

    void deleteAuthor(Long id);

    List<AuthorDto> createAuthorDtos(String authors);

    List<String> extractFullNames(String input);
}
