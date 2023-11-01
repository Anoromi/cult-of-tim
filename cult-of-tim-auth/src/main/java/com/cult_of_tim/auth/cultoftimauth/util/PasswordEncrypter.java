package com.cult_of_tim.auth.cultoftimauth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypter implements PasswordEncoder {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean checkPassword(String inputPassword, String storedEncryptedPassword) {
        return passwordEncoder.matches(inputPassword, storedEncryptedPassword);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
