package com.cult_of_tim.auth.cultoftimauth.validator.impl;

import com.cult_of_tim.auth.cultoftimauth.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An interface implementation for email and password validation while registration.
 * @author Oleksandr Severhin
 */
public class EmailValidatorImpl implements EmailValidator {

    private static String EMAIL_REGEX;


    @Qualifier("getEmailRegex")
    private void setEmailRegex(String emailRegex) {
        EMAIL_REGEX = emailRegex;
    }

    /**
     * Checks if email is valid
     * @param email a String email parameter
     * @return true if mail is valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
