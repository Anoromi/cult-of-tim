package com.example.cult_of_tim.cultoftim.controller;


import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.example.cult_of_tim.cultoftim.service.FundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String showAddFundsPage(Model model, @AuthenticationPrincipal UserDTO userDTO) {
        double currentBalance = userDTO.getBalance();
        model.addAttribute("currentBalance", "Current Balance: " + currentBalance + " UAH");
        return "addFunds";
    }

    @PostMapping("/addFunds")
    public String addFunds(@RequestParam("amount") double amount, @AuthenticationPrincipal UserDTO userDTO) {
        userDTO.setBalance((int) (userDTO.getBalance()+amount));

        fundsService.updateUserBalance(userDTO);

        return "redirect:/addFunds";
    }
}