package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.controller.request.LoginUser;
import com.example.cult_of_tim.cultoftim.controller.request.RegisterUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(name = "/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid RegisterUser registerUser) throws AuthException {
        userService.registerUser(registerUser.getUsername(), registerUser.getEmail(), registerUser.getPassword());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    //@PostMapping(name = "/auth/login")
    //public ResponseEntity<String> login(@RequestBody @Valid LoginUser login) {
    //    String token = userService.login(login.getUsernameOrEmail(), login.getPassword()).getToken();

    //    return new ResponseEntity<>(token, HttpStatus.OK);
    //}

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<String> handleAuthError(AuthException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
