package com.cult_of_tim.auth.cultoftimauth.service;



import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {


    Optional<User> getUserById(UUID id);

    Optional<User> getUserByEmail(String email);


    UUID registerUser(String username, String email, String password) throws AuthException;

    User updateUser(String email, User updatedUser) throws IllegalArgumentException;

    void deleteUser(String email);

    boolean login(String emailOrUsername, String password) throws AuthException;
}
