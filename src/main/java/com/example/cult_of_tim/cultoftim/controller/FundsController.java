package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.service.FundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FundsController {

    @Autowired
    private FundsService fundsService;

    @GetMapping("/addFunds")
    public String showAddFundsPage(Model model) {
        double currentBalance = fundsService.getCurrentBalance();
        model.addAttribute("currentBalance", "Current Balance: " + currentBalance + " UAH");
        return "addFunds";
    }

    @PostMapping("/addFunds")
    public String addFunds(@RequestParam("amount") double amount) {
        fundsService.addFunds(amount);
        return "redirect:/addFunds";
    }
}