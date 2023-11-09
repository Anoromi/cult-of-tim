package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.controller.request.RegisterUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class LoginPageController {
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        RegisterUser userDto = new RegisterUser();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") RegisterUser user,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
        } catch (AuthException e) {
            if(e.getMessage().contains("Password"))
                return "redirect:/register?password";
            else
                return "redirect:/register?error";
        }
        return "redirect:/register?success";
    }
}
