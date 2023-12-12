package com.example.cult_of_tim.cultoftim;

import com.cult_of_tim.auth.cultoftimauth.converter.UserConverter;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserTokenRepository;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.cult_of_tim.auth.cultoftimauth.service.impl.UserMockService;
import com.cult_of_tim.auth.cultoftimauth.util.WithoutPasswordChecker;
import com.cult_of_tim.auth.cultoftimauth.validator.impl.EmailValidatorImpl;
import com.cult_of_tim.auth.cultoftimauth.validator.impl.PasswordValidatorImpl;
import com.cult_of_tim.auth.cultoftimauth.validator.impl.TokenValidatorWithExpiration;
import com.cult_of_tim.auth.cultoftimauth.validator.regex.EmailPasswordValidationRegex;
import com.example.cult_of_tim.cultoftim.auth.UserRoles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserMockService.class, EmailValidatorImpl.class, PasswordValidatorImpl.class, UserConverter.class, WithoutPasswordChecker.class, EmailPasswordValidationRegex.class, TokenValidatorWithExpiration.class}
)
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserTokenRepository userTokenRepository;

    @Autowired
    UserService userService;


    @Test
    void test1() {
        var testedUser = Optional.of(new User(UUID.randomUUID(), "test", "password", "example@email.com", UserRoles.DEFAULT, 0));
        Mockito.when(userRepository.findByEmail("example@email.com"))
                .thenReturn(testedUser);
        Mockito.when(userRepository.findByUsername("test"))
                .thenReturn(testedUser);

        Mockito.when(userTokenRepository.save(Mockito.any())).then(v -> {
            UserToken t = v.getArgument(0);
            t.setTokenId(UUID.randomUUID());
            return t;
        });

        var user = userService.login("example@email.com", "wrongpassword");
        Assertions.assertNotNull(user.getToken());


        var user2 = userService.login("test", "wrongpassword");
        Assertions.assertNotNull(user.getToken());

        Assertions.assertEquals(user.getUser(), user2.getUser());

    }
}
