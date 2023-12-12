package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.example.cult_of_tim.cultoftim.auth.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Controller
@AllArgsConstructor
public class MainController {
    UserContext userContext;

    @GetMapping(value = "/")
    public String getTemplate(@AuthenticationPrincipal UserDTO user) {
        return "main";
    }
}