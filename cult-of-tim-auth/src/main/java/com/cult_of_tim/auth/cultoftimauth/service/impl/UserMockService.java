package com.cult_of_tim.auth.cultoftimauth.service.impl;

import com.cult_of_tim.auth.cultoftimauth.converter.UserConverter;
import com.cult_of_tim.auth.cultoftimauth.dto.LoggedUserDTO;
import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
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
import com.cult_of_tim.auth.cultoftimauth.validator.TokenValidator;
import lombok.AllArgsConstructor;
import org.slf4j.*;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserMockService implements UserService {
    private final UserRepository userRepository;

    private final EmailValidator emailValidator;

    private final PasswordValidator passwordValidator;

    private final UserChecker userChecker;

    private final UserTokenRepository userTokenRepository;

    private final UserConverter userConverter;

    private final TokenValidator tokenValidator;

    private final Logger logger = LoggerFactory.getLogger(UserMockService.class);

    public final Marker authMarker = MarkerFactory.getMarker("AUTH");


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
        newUser.setRole("Default");

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
    public LoggedUserDTO login(String emailOrUsername, String password) throws IllegalArgumentException {
        if (logger.isDebugEnabled()) {
            logger.debug(authMarker, "Attempting login for user with email/username: {}", emailOrUsername);
        }

        Optional<User> user = userChecker.lookupUser(emailOrUsername, password);

        if (user.isPresent()) {
            try {
                UserToken userToken = new UserToken();
                userToken.setUser(user.get());
                userToken.setExpiresAt(getExpireDate(12));
                userTokenRepository.save(userToken);

                if (logger.isInfoEnabled()) {
                    logger.info(authMarker, "User with email/username {} has successfully logged in", emailOrUsername);
                }
                return LoggedUserDTO.builder().token(userToken.getTokenId().toString()).user(userConverter.toDTO(user.get())).build();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info(authMarker, "Login attempt failed for user with email/username: {}", emailOrUsername);
        }
        throw new IllegalArgumentException("Login attempt failed for user with email/username: " + emailOrUsername);
    }

    @Override
    public Optional<UserDTO> getUserByToken(String token) {
        var userToken = userTokenRepository.findById(UUID.fromString(token));
        System.out.println(userTokenRepository.findAll());
        if (userToken.isEmpty()) {
            return Optional.empty();
        }
        var user = tokenValidator.validateToken(
                userToken.get());
        return user.map(userConverter::toDTO);
    }

    @Override
    public void setUserRole(UUID id, String role) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return;

        var user = optionalUser.get();
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public User getUserBy(String emailOrUsername, String password) throws AuthException {
        var optionalUser = userChecker.lookupUser(emailOrUsername, password);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new AuthException("Username or password is wrong");
    }

    private Date getExpireDate(Integer hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
