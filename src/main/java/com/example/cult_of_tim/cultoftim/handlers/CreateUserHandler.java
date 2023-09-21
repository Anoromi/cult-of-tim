package com.example.cult_of_tim.cultoftim.handlers;

import com.example.cult_of_tim.cultoftim.models.User;
import com.example.cult_of_tim.cultoftim.service.UserService;
import com.example.cult_of_tim.cultoftim.util.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles User creation
 * @author Oleksndr Severhin
 */
@Service
public class CreateUserHandler {

    private final UserService userMockService;

    @Autowired
    public CreateUserHandler(UserService userMockService)
    {
        this.userMockService = userMockService;
    }

    // A regex to check an email
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]" +
            "+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
            "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]" +
            "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
            "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
            "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
            "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]" +
            ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]" +
            "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    /**
     * Checks email, password for being valid, encrypts the password and creates the User
     * @param email a String email parameter
     * @param password a String password parameter
     * @return new User
     * @throws Exception //ValidationException
     */
    public User createUser(String email, String password) throws Exception {
        // Validate email and password
        if (!isValidEmail(email)) {
            throw new Exception("Invalid email address");
        }

        if (!isValidPassword(password)) {
            throw new Exception("Invalid password");
        }

        String encryptedPassword = encrypt(password);
        User user = new User();
        userMockService.registerUser(email, encryptedPassword);

        return user;
    }

    /**
     * Checks if email is valid
     * @param email a String email parameter
     * @return true if mail is valid, false otherwise
     */
    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks if password is at least eight symbols long, has at least one upper case letter,
     * has at least one digit and at least one special symbol
     * @param password a String password parameter
     * @return true, if password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        // Check if the password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }
        // Check if the password contains at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Check if the password contains at least one digit (number)
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // Check if the password contains at least one special symbol
        return password.matches(".*[!@#$%^&*()-_+=<>?].*");
    }

    /**
     * This method encrypts the password
     * @param password a String that is a password parameter
     * @return encrypted password
     */
    private String encrypt(String password) {
        return PasswordEncrypter.encryptPassword(password);
    }
}
