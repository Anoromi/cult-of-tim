package com.cult_of_tim.auth.cultoftimauth.validator.impl;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.cult_of_tim.auth.cultoftimauth.validator.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TokenValidatorWithExpiration implements TokenValidator {
    private final UserRepository userRepository;

    @Autowired
    public TokenValidatorWithExpiration(UserRepository userDao) {
        this.userRepository = userDao;
    }

    private static boolean isExpired(UserToken token) {
        Date currentDate = new Date();
        Date expireDate = token.getExpiresAt();
        return expireDate != null && expireDate.before(currentDate);
    }

    @Override
    public Optional<User> validateToken(UserToken token) {
        return Optional.ofNullable(token.getUser());
    }
}