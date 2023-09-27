package com.cult_of_tim.auth.cultoftimauth.service.impl;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.util.PasswordEncrypter;
import com.cult_of_tim.auth.cultoftimauth.validator.EmailValidator;
import com.cult_of_tim.auth.cultoftimauth.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMockService implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private UserChecker passwordChecker;

    //@Autowired
    //public UserMockService(UserDao userDao, EmailValidator emailValidator, PasswordValidator passwordValidator, UserChecker passwordChecker) {
    //    this.userDao = userDao;
    //    this.emailValidator = emailValidator;
    //    this.passwordValidator = passwordValidator;
    //    this.passwordChecker = passwordChecker;
    //}

    @Override
    public Optional<User> getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Long registerUser(String email, String password) throws IllegalArgumentException {
        User newUser = new User();

        if (!emailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid!");
        }
        if (!passwordValidator.isValidPassword(password)) {
            throw new IllegalArgumentException("Password is invalid!");
        }

        newUser.setEmail(email);
        newUser.setPassword(PasswordEncrypter.encryptPassword(password));
        return userDao.createUser(newUser);
    }

    @Override
    public User updateUser(String email, User updatedUser) throws IllegalArgumentException {
        User existingUser = userDao.getUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(PasswordEncrypter.encryptPassword(updatedUser.getPassword()));

        return userDao.updateUser(existingUser);
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userDao.getUserByEmail(email);
        user.ifPresent(value -> userDao.deleteUserById(value.getId()));
    }

    @Override
    public boolean login(String email, String password) throws IllegalArgumentException {
        var user = passwordChecker.lookupUser(email, password);

        return user.isPresent();
    }
}
