package com.cult_of_tim.auth.cultoftimauth.util;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class WithoutPasswordChecker implements UserChecker {
    private final UserRepository userRepository;

    public WithoutPasswordChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> lookupUser(String email, String password) {
        return userRepository.findByEmail(email);
    }
}
