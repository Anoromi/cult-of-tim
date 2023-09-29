package com.cult_of_tim.auth.cultoftimauth.validator.impl;

import com.cult_of_tim.auth.cultoftimauth.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidatorImpl implements PasswordValidator {

    private static String AT_LEAST_ONE_UPPERCASE_LETTER;
    private static String AT_LEAST_ONE_DIGIT;
    private static String AT_LEAST_ONE_SPECIAL_SYMBOL;




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
