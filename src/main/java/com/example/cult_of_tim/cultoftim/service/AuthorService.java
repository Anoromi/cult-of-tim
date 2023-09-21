package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> getAuthorById(Long id);

    List<Author> getAllAuthors();

    Long createAuthor(String name);

    void updateAuthor(Long id, Author updatedAuthor);

    void deleteAuthor(Long id);
}
