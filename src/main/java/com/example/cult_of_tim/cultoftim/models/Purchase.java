package com.example.cult_of_tim.cultoftim.models;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {

    private Long id;
    private LocalDateTime purchaseDate;
    private User user;
    private List<Book> bookItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBookItem() {
        return bookItem;
    }

    public void setBookItem(List<Book> bookItem) {
        this.bookItem = bookItem;
    }
}
