package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.controller.request.RegisterUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup",
    consumes = "application/json")
    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody()
    )
    public ResponseEntity<Void> signup(@RequestBody @Valid RegisterUser registerUser) throws AuthException {
        userService.registerUser(registerUser.getUsername(), registerUser.getEmail(), registerUser.getPassword());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // TODO
    //@PostMapping(name = "/login")
    //public ResponseEntity<String> login(@RequestMapping LoginUser login) throws AuthException {
    //    userService.login(login.getUsernameOrEmail(), login.getPassword());
    //    retturnun
    //}


    @ExceptionHandler({AuthException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad auth data")
    public ResponseEntity<String> handleAuthError(AuthException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
