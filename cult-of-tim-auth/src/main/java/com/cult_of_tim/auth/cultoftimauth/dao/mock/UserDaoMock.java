package com.cult_of_tim.auth.cultoftimauth.dao.mock;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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
    public Long createUser(User user) {
        user.setId(nextUserId++);
        userMap.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public User updateUser(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userMap.remove(id);
    }

    @Override
    public Optional<UserToken> getUserTokenFor(UUID token) {
        return Optional.empty();
    }
}
