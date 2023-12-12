package com.cult_of_tim.auth.cultoftimauth.validator.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Regex for email and password validation
 * @author Oleksandr Severhin
 */
@Configuration
public class EmailPasswordValidationRegex {

    /**
     * Returns regex for Email validation
     */
    @Bean
    public static String getEmailRegex()
    {
        return "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]" +
                "+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
                "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]" +
                "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
                "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]" +
                ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]" +
                "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    }

    /**
     * Returns regex for checking if password has at least one uppercase letter
     */
    @Bean
    public static String getAtLeastOneUppercaseLetter()
    {
        return ".*[A-Z].*";
    }

    /**
     * Returns regex for checking if password has at least one digit
     */
    @Bean
    public static String getAtLeastOneDigit() {
        return ".*\\d.*";
    }

    /**
     * Returns regex for checking if password has at least one special symbol
     */
    @Bean
    public static String getAtLeastOneSpecialSymbol() {
        return ".*[!@#$%^&*()-_+=<>?].*";
    }

}
