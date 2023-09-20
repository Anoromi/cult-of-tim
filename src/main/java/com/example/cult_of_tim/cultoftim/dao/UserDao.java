package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUser(String token);

    void createAccount(String email, String encryptedPassword) throws IllegalArgumentException;

    String login(String email, String encryptedPassword) throws IllegalArgumentException;

    void deleteAccount(String email);

}
