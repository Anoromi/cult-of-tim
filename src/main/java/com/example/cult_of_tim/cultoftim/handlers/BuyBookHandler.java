package com.example.cult_of_tim.cultoftim.handlers;

import com.example.cult_of_tim.cultoftim.dao.BookDao;
import com.example.cult_of_tim.cultoftim.models.Book;
import com.example.cult_of_tim.cultoftim.models.User;



public class BuyBookHandler {
    private final BookDao bookDao;

    public BuyBookHandler(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void processBuy(User user, Book book) {
        bookDao.buyBook(user, book);
    }
}
