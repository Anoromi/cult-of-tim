package com.example.cult_of_tim.cultoftim.models;

import java.util.List;


public class Book {
    private Long id;

    private String title;

    private List<Long> authorIDs;

    private List<Category> categories;
    private int quantity;
    private boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAuthorIDs() {
        return authorIDs;
    }

    public void setAuthorIDs(List<Long> authorIDs) {
        this.authorIDs = authorIDs;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
