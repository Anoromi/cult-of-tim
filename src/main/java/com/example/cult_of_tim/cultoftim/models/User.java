package com.example.cult_of_tim.cultoftim.models;

import jakarta.persistence.*;

import java.util.List;


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private List<Long> booksIDs;
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Long> getBooks() {
        return booksIDs;
    }

}
