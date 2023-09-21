package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.dao.AuthorDao;
import com.example.cult_of_tim.cultoftim.models.Author;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorMockService implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorMockService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorDao.getAuthorById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public Long createAuthor(String name) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        return authorDao.createAuthor(newAuthor);
    }

    @Override
    public Author updateAuthor(Long id, Author updatedAuthor) {
        updatedAuthor.setId(id);
        return authorDao.updateAuthor(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorDao.deleteAuthorById(id);
    }
}
