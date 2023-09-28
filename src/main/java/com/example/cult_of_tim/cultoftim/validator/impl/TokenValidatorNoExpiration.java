package com.example.cult_of_tim.cultoftim.validator.impl;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import com.example.cult_of_tim.cultoftim.validator.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenValidatorNoExpiration  implements TokenValidator {

    private UserDao userDao;

    @Autowired
    public TokenValidatorNoExpiration(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Validates the token and returns the user
     * @param token a String given
     * @return User
     */
    @Override
    public Optional<User> validateToken(UserToken token) {
        String tokenData = userDao.findByToken(token);
        if (tokenData != null) {
            return userDao.getUserById(token.getUserId());
        } else {
            return null;
        }
    }
}