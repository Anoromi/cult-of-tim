package com.example.cult_of_tim.cultoftim.service.impl;


import com.example.cult_of_tim.cultoftim.models.Author;
import com.example.cult_of_tim.cultoftim.repositories.AuthorRepository;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorMockService implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorMockService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author createAuthor(String firstName, String lastName) {
        Author newAuthor = new Author();
        newAuthor.setFirstName(firstName);
        newAuthor.setLastName(firstName);
        return authorRepository.save(newAuthor);
    }

    @Override
    public Author updateAuthor(Long id, Author updatedAuthor) {
        updatedAuthor.setId(id);
        return authorRepository.save(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
