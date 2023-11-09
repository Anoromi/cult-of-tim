package com.example.cult_of_tim.cultoftim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginPageController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
