package com.example.cult_of_tim.cultoftim.dao.mock;

import com.example.cult_of_tim.cultoftim.dao.AuthorDao;
import com.example.cult_of_tim.cultoftim.models.Author;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthorDaoMock implements AuthorDao {

    private final Map<Long, Author> authorMap = new HashMap<>();
    private Long nextAuthorId = 1L;

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return Optional.ofNullable(authorMap.get(id));
    }

    @Override
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authorMap.values());
    }

    @Override
    public Long createAuthor(Author author) {
        author.setId(nextAuthorId++);
        authorMap.put(author.getId(), author);
        return author.getId();
    }

    @Override
    public Author updateAuthor(Author author) {
        authorMap.put(author.getId(), author);
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorMap.remove(id);
    }
}

