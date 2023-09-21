package com.example.cult_of_tim.cultoftim.dao.mock;

import com.example.cult_of_tim.cultoftim.dao.AuthorDao;
import com.example.cult_of_tim.cultoftim.models.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public void createAuthor(Author author) {
        author.setId(nextAuthorId++);
        authorMap.put(author.getId(), author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorMap.put(author.getId(), author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorMap.remove(id);
    }
}

