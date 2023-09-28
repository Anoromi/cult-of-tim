package com.cult_of_tim.auth.cultoftimauth.dao;



import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    Long createUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);

    Optional<UserToken> getUserTokenFor(UUID token);
}
