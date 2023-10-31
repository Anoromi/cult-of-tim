package com.cult_of_tim.auth.cultoftimauth.service.impl;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserTokenRepository;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.cult_of_tim.auth.cultoftimauth.util.PasswordEncrypter;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.validator.EmailValidator;
import com.cult_of_tim.auth.cultoftimauth.validator.PasswordValidator;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMockService implements UserService {
    private final UserRepository userRepository;

    private final UserTokenRepository userTokenRepository;

    private final EmailValidator emailValidator;

    private final PasswordValidator passwordValidator;

    private final UserChecker passwordChecker;

    private final Logger logger = LoggerFactory.getLogger(UserMockService.class);

    public final Marker authMarker = MarkerFactory.getMarker("AUTH");

    @Autowired
    public UserMockService(UserRepository userRepository, EmailValidator emailValidator, PasswordValidator passwordValidator, UserChecker passwordChecker) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.passwordChecker = passwordChecker;
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UUID registerUser(String username, String email, String password) throws AuthException {
        User newUser = new User();

        if (!emailValidator.isValidEmail(email)) {
            throw new AuthException("Email is invalid!");
        }
        if (!passwordValidator.isValidPassword(password)) {
            throw new AuthException("Password is invalid!");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new AuthException("Email is already used");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new AuthException("Username is already used");
        }

        newUser.setEmail(email);
        newUser.setPassword(PasswordEncrypter.encryptPassword(password));
        newUser.setUsername(username);

        MDC.put("user", username);
        logger.info(authMarker, "Registered user");
        MDC.remove("user");

        return userRepository.save(newUser).getUserId();
    }

    @Override
    public User updateUser(String email, User updatedUser) throws IllegalArgumentException {
        User existingUser = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(PasswordEncrypter.encryptPassword(updatedUser.getPassword()));

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(value -> userRepository.deleteById(value.getUserId()));
    }

    @Override
    public boolean login(String emailOrUsername, String password) throws IllegalArgumentException{
        if (logger.isDebugEnabled()) {
            logger.debug(authMarker, "Attempting login for user with email/username: {}", emailOrUsername);
        }

        Optional<User> user = passwordChecker.lookupUser(emailOrUsername, password);

        if (user.isPresent()) {
            UserToken userToken = new UserToken();
            userToken.setUser(user.get());
            userToken.setExpiresAt(getExpireDate(12));
            userTokenRepository.save(userToken);

            if (logger.isInfoEnabled()) {
                logger.info(authMarker, "User with email/username {} has successfully logged in", emailOrUsername);
            }
            return true;
        }

        if (logger.isInfoEnabled()) {
            logger.info(authMarker, "Login attempt failed for user with email/username: {}", emailOrUsername);
        }
        return false;
    }

    private Date getExpireDate (Integer hours){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    @Override
    public void setUserRole(UUID id, String role) {
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return;
        var user = optionalUser.get();

        user.setRole(role);
        userRepository.save(user);
    }
}
