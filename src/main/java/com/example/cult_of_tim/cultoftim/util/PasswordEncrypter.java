package com.example.cult_of_tim.cultoftim.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypter {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean checkPassword(String inputPassword, String storedEncryptedPassword) {
        return passwordEncoder.matches(inputPassword, storedEncryptedPassword);
    }
}
