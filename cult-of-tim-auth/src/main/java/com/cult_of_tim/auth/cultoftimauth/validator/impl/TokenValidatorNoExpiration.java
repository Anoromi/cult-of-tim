package com.cult_of_tim.auth.cultoftimauth.validator.impl;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.cult_of_tim.auth.cultoftimauth.validator.TokenValidator;

import java.util.Date;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TokenValidatorNoExpiration  implements TokenValidator {

    private UserRepository userRepository;

    public TokenValidatorNoExpiration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Validates the token and returns the user
     * @param token a String given
     * @return User
     */
    @Override
    public Optional<User> validateToken(UserToken token) {
        if(token.getExpiresAt().after(new Date()))
            return Optional.ofNullable(token.getUser());
        return Optional.empty();
    }
}