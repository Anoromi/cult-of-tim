package com.example.cult_of_tim.cultoftim.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    @GetMapping(value = "/")
    public String getTemplate() {

        return "main";
    }
}