package com.cult_of_tim.auth.cultoftimauth.validator;

/**
 * An interface for email and password validation while registration.
 * @author Oleksandr Severhin
 */
public interface EmailValidator {

    /**
     * Checks if email is valid
     * @param email a String email parameter
     * @return true if mail is valid, false otherwise
     */
    boolean isValidEmail(String email);

}
