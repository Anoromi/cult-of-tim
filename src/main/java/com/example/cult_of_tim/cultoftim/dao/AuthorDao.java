package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.Author;
import com.example.cult_of_tim.cultoftim.models.Book;

import java.util.List;

public interface AuthorDao {

    List<Author> getAuthorsForBook(Book book);


}
