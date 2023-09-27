package com.cult_of_tim.auth.cultoftimauth.util;


import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnMissingBean(UserChecker.class)
public class UserCheckerImpl implements UserChecker {

    private final UserDao userDao;

    @Hehe
    @Autowired
    public UserCheckerImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public Optional<User> lookupUser(String email, String password) {
        Optional<User> userOptional = userDao.getUserByEmail(email);
        if (userOptional.isPresent() && PasswordEncrypter.checkPassword(password, userOptional.get().getPassword())) {
            return userOptional;
        }

        return Optional.empty();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @interface Hehe {}
}
