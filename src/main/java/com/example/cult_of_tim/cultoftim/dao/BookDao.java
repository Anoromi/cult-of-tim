package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.Book;
import com.example.cult_of_tim.cultoftim.models.User;

import java.util.List;

public interface BookDao {

    List<Book> getBooks();

    List<Book> getUserBooks(User user);


    void buyBook(User user, Book book);

}
