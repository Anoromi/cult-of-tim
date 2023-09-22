package com.example.cult_of_tim.cultoftim.validator.impl;

import com.example.cult_of_tim.cultoftim.validator.EmailPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An interface implementation for email and password validation while registration.
 * @author Oleksandr Severhin
 */
@Service
public class EmailPasswordValidatorImpl implements EmailPasswordValidator {

    private static String EMAIL_REGEX;
    private static String AT_LEAST_ONE_UPPERCASE_LETTER;
    private static String AT_LEAST_ONE_DIGIT;
    private static String AT_LEAST_ONE_SPECIAL_SYMBOL;

    @Autowired
    @Qualifier("getEmailRegex")
    private void setEmailRegex(String emailRegex) {
        EMAIL_REGEX = emailRegex;
    }

    @Autowired
    @Qualifier("getAtLeastOneUppercaseLetter")
    private void setAtLeastOneUppercaseLetter(String oneUpperCaseRegex){
        AT_LEAST_ONE_UPPERCASE_LETTER = oneUpperCaseRegex;
    }

    @Autowired
    @Qualifier("getAtLeastOneDigit")
    private void setAtLeastOneDigit(String oneDigitRegex) {
        AT_LEAST_ONE_DIGIT = oneDigitRegex;
    }

    @Autowired
    @Qualifier("getAtLeastOneSpecialSymbol")
    private void setAtLeastOneSpecialSymbol(String oneSpecSymbolRegex) {
        AT_LEAST_ONE_SPECIAL_SYMBOL = oneSpecSymbolRegex;
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

    /**
     * Checks if password is at least eight symbols long, has at least one upper case letter,
     * has at least one digit and at least one special symbol
     * @param password a String password parameter
     * @return true, if password is valid, false otherwise
     */
    public boolean isValidPassword(String password) {
        // Check if the password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }
        // Check if the password contains at least one uppercase letter
        if (!password.matches(AT_LEAST_ONE_UPPERCASE_LETTER)) {
            return false;
        }
        // Check if the password contains at least one digit (number)
        if (!password.matches(AT_LEAST_ONE_DIGIT)) {
            return false;
        }
        // Check if the password contains at least one special symbol
        return password.matches(AT_LEAST_ONE_SPECIAL_SYMBOL);
    }
}
