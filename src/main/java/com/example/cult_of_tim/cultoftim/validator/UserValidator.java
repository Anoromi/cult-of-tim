package com.example.cult_of_tim.cultoftim.validator;

import com.example.cult_of_tim.cultoftim.models.User;

public interface UserValidator {

    User validate(String token) throws IllegalArgumentException;
}
