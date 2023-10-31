package com.cult_of_tim.auth.cultoftimauth.util;


import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class UserCheckerImpl implements UserChecker {

    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserCheckerImpl.class);

    public final Marker lookupUserMarker = MarkerFactory.getMarker("LOOKUP-USER");

    public UserCheckerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> lookupUser(String emailOrUsername, String password) {
        if (logger.isDebugEnabled()) {
            logger.debug(lookupUserMarker, "Looking up user with email: {}", emailOrUsername);
        }

        Optional<User> userOptional = userRepository.findByEmail(emailOrUsername);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(emailOrUsername);
        }

        if (userOptional.isPresent() && PasswordEncrypter.checkPassword(password, userOptional.get().getPassword())) {
            if (logger.isDebugEnabled()) {
                logger.debug(lookupUserMarker, "User found and password matches for email: {}", emailOrUsername);
            }
            return userOptional;
        }

        if (logger.isDebugEnabled()) {
            logger.debug(lookupUserMarker, "User not found or password does not match for email: {}", emailOrUsername);
        }

        return Optional.empty();
    }

}
