package com.example.cult_of_tim.cultoftim.models;

import jakarta.persistence.*;

import java.util.List;


public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private List<Long> authorIDs;

    private List<Category> categories;

    public Long getId() {
        return id;
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

    public List<Category> getCategories() {
        return categories;
    }
}
