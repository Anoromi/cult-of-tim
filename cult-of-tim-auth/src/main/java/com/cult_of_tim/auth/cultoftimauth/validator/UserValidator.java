package com.cult_of_tim.auth.cultoftimauth.validator;


import com.cult_of_tim.auth.cultoftimauth.model.User;

public interface UserValidator {

    User validate(String token) throws IllegalArgumentException;
}
