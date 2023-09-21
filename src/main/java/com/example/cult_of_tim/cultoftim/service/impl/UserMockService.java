package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.dao.UserDao;
import com.example.cult_of_tim.cultoftim.models.User;
import com.example.cult_of_tim.cultoftim.service.UserService;
import com.example.cult_of_tim.cultoftim.util.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMockService implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserMockService(UserDao userDao) {
        this.userDao = userDao;
    }

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
        newUser.setEmail(email);
        newUser.setPassword(PasswordEncrypter.encryptPassword(password));

        userDao.createUser(newUser);

        return newUser.getId();
    }

    @Override
    public User updateUser(String email, User updatedUser) throws IllegalArgumentException {
        User existingUser = userDao.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(PasswordEncrypter.encryptPassword(updatedUser.getPassword()));

        userDao.updateUser(existingUser);

        return existingUser;
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userDao.getUserByEmail(email);
        user.ifPresent(value -> userDao.deleteUserById(value.getId()));
    }

    @Override
    public boolean login(String email, String password) throws IllegalArgumentException {
        Optional<User> userOptional = userDao.getUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return PasswordEncrypter.checkPassword(password, user.getPassword());
        }

        return false;
    }
}
