package com.example.cult_of_tim.cultoftim.dao.mock;

import com.example.cult_of_tim.cultoftim.dao.BookDao;
import com.example.cult_of_tim.cultoftim.models.Book;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookDaoMock implements BookDao {

    private final Map<Long, Book> bookMap = new HashMap<>();
    private Long nextBookId = 1L;

    @Override
    public Optional<Book> getBookById(Long id) {
        return Optional.ofNullable(bookMap.get(id));
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookMap.values()) {
            if (book.getAuthorIDs().contains(authorId)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> getBooksByCategoryId(Long categoryId) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookMap.values()) {
            if (book.getCategoryIDs().contains(categoryId)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public Long createBook(Book book) {
        book.setId(nextBookId++);
        bookMap.put(book.getId(), book);
        return book.getId();
    }

    @Override
    public Book updateBook(Book book) {
        bookMap.put(book.getId(), book);
        return book;
    }

    @Override
    public void deleteBookById(Long id) {
        bookMap.remove(id);
    }
}