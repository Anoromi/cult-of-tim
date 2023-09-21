package com.example.cult_of_tim.cultoftim.dao.mock;

import com.example.cult_of_tim.cultoftim.dao.UserDao;
import com.example.cult_of_tim.cultoftim.models.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoMock implements UserDao {

    private final Map<Long, User> userMap = new HashMap<>();
    private Long nextUserId = 1L;

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void createUser(User user) {
        user.setId(nextUserId++);
        userMap.put(user.getId(), user);
    }

    @Override
    public void updateUser(User user) {
        userMap.put(user.getId(), user);
    }

    @Override
    public void deleteUserById(Long id) {
        userMap.remove(id);
    }

    /*@Override
    public Optional<User> getUser(String token) {
        return Optional.empty();
    }

    @Override
    public void createAccount(String email, String encryptedPassword) throws IllegalArgumentException {
        User newUser = new User(email, encryptedPassword);
        userList.put(email, newUser);
    }

    @Override
    public String login(String email, String encryptedPassword) throws IllegalArgumentException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    @Override
    public void deleteAccount(String email) {
        userList.remove(getUser());
    }*/
}
