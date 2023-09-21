package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    Long registerUser(String email, String password) throws IllegalArgumentException;

    User updateUser(String email, User updatedUser) throws IllegalArgumentException;

    void deleteUser(String email);

    boolean login(String email, String password) throws IllegalArgumentException;
}
