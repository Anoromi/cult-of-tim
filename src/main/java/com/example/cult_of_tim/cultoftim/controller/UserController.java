package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.requestData.LoginUser;
import com.example.cult_of_tim.cultoftim.requestData.RegisterUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid RegisterUser registerUser) throws AuthException {
        userService.registerUser(registerUser.username, registerUser.email, registerUser.password);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // TODO
    //@PostMapping(name = "/login")
    //public ResponseEntity<String> login(@RequestMapping LoginUser login) throws AuthException {
    //    userService.login(login.getUsernameOrEmail(), login.getPassword());
    //    retturnun
    //}


    @ExceptionHandler({AuthException.class})
    public ResponseEntity<String> handleAuthError(AuthException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }
}
