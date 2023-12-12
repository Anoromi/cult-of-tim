package com.cult_of_tim.auth.cultoftimauth.util;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Optional;

public class WithoutPasswordChecker implements UserChecker {
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(WithoutPasswordChecker.class);

    public final Marker lookupUserMarker = MarkerFactory.getMarker("LOOKUP-USER");

    public WithoutPasswordChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> lookupUser(String email, String password) {
        if (logger.isDebugEnabled()) {
            logger.debug(lookupUserMarker, "Looking up user with email: {}", email);
        }

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            if (logger.isDebugEnabled()) {
                logger.debug(lookupUserMarker, "Found user with email: {}", email);
            }
            return user;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug(lookupUserMarker, "User not found for email: {}", email);
            }
        }

        user = userRepository.findByUsername(email);

        return user;

    }
}
