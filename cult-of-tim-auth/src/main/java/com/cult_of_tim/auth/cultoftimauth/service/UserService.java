package com.cult_of_tim.auth.cultoftimauth.service;



import com.cult_of_tim.auth.cultoftimauth.model.User;

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
