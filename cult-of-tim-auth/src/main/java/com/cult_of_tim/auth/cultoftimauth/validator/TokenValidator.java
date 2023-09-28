package com.cult_of_tim.auth.cultoftimauth.validator;

import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import com.example.cult_of_tim.cultoftim.models.User;

import java.util.Optional;

/**
 * Token validator interface
 * @author Oleksandr Severhin
 */
public interface TokenValidator {

    /**
     * Validates the token and returns the user
     * @param token a String given
     * @return User
     */
    Optional<User> validateToken(UserToken token);
}
