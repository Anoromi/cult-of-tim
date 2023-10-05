package com.cult_of_tim.auth.cultoftimauth.service.impl;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.cult_of_tim.auth.cultoftimauth.util.PasswordEncrypter;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.validator.EmailValidator;
import com.cult_of_tim.auth.cultoftimauth.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserMockService implements UserService {
    private final UserRepository userRepository;

    private final EmailValidator emailValidator;

    private final PasswordValidator passwordValidator;

    private final UserChecker passwordChecker;

    @Autowired
    public UserMockService(UserRepository userRepository, EmailValidator emailValidator, PasswordValidator passwordValidator, UserChecker passwordChecker) {
        this.userRepository = userRepository;
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
    public UUID registerUser(String username, String email, String password) throws IllegalArgumentException {
        User newUser = new User();

        if (!emailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid!");
        }
        if (!passwordValidator.isValidPassword(password)) {
            throw new IllegalArgumentException("Password is invalid!");
        }

        newUser.setEmail(email);
        newUser.setPassword(PasswordEncrypter.encryptPassword(password));
        return userRepository.save(newUser).getId();
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
        user.ifPresent(value -> userRepository.deleteById(value.getId()));
    }

    @Override
    public boolean login(String email, String password) throws IllegalArgumentException {
        var user = passwordChecker.lookupUser(email, password);

        return user.isPresent();
    }
}
