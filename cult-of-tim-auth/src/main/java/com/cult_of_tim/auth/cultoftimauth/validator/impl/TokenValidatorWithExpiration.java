package com.cult_of_tim.auth.cultoftimauth.validator.impl;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import com.cult_of_tim.auth.cultoftimauth.validator.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TokenValidatorWithExpiration implements TokenValidator {
    private final UserDao userDao;

    @Autowired
    public TokenValidatorWithExpiration(UserDao userDao) {
        this.userDao = userDao;
    }

    private static boolean isExpired(UserToken token) {
        Date currentDate = new Date();
        Date expireDate = token.expiresAt;
        return expireDate != null && expireDate.before(currentDate);
    }

    @Override
    public Optional<User> validateToken(UserToken token) {
        String tokenData = userDao.findByToken(token);

        if (tokenData != null) {
            if (isExpired(token)) {
                return null;
            } else {
                return userDao.getUserById(token.userId);
            }
        } else {
            return null;
        }
    }
}