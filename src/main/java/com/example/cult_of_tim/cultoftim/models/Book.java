package com.example.cult_of_tim.cultoftim.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Author author; // не впевний що правильно зв'язки налаштував

    @ManyToMany
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

    public Author getAuthor() {
        return author;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
