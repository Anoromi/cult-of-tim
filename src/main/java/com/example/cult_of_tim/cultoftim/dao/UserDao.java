package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.User;

public interface UserDao {

    User getUser(String token);

    void createAccount(String email, String encryptedPassword);

    void login(String email, String encryptedPassword);

    void deleteAccount(String email);

}
