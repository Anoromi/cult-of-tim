package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> getAuthorById(Long id);

    List<Author> getAllAuthors();

    Long createAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);



}

