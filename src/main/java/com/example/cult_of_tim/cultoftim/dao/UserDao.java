package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    Long createUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);
}
