package com.example.cult_of_tim.cultoftim.validator;

/**
 * An interface for email and password validation while registration.
 * @author Oleksandr Severhin
 */
public interface EmailPasswordValidator {

    /**
     * Checks if email is valid
     * @param email a String email parameter
     * @return true if mail is valid, false otherwise
     */
    boolean isValidEmail(String email);

    /**
     * Checks if password is at least eight symbols long, has at least one upper case letter,
     * has at least one digit and at least one special symbol
     * @param password a String password parameter
     * @return true, if password is valid, false otherwise
     */
    boolean isValidPassword(String password);
}
