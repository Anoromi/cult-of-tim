package com.cult_of_tim.auth.cultoftimauth.util;


import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;

import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class UserCheckerImpl implements UserChecker {

    private final UserRepository userRepository;

    public UserCheckerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> lookupUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && PasswordEncrypter.checkPassword(password, userOptional.get().getPassword())) {
            return userOptional;
        }

        return Optional.empty();
    }

}
