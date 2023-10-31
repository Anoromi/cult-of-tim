package com.cult_of_tim.auth.cultoftimauth.util;

import com.cult_of_tim.auth.cultoftimauth.model.User;

import java.util.Optional;

public interface UserChecker {

    Optional<User> lookupUser(String emailOrUsername, String password);
}
