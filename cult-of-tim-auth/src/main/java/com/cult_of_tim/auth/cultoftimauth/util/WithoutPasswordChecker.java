package com.cult_of_tim.auth.cultoftimauth.util;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.model.User;

import java.util.Optional;

public class WithoutPasswordChecker implements UserChecker {
    private final UserDao userDao;

    public WithoutPasswordChecker(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> lookupUser(String email, String password) {
        return userDao.getUserByEmail(email);
    }
}
