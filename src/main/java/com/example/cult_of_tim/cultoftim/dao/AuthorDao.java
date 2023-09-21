package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> getAuthorById(Long id);

    List<Author> getAllAuthors();

    void createAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthorById(Long id);
}

