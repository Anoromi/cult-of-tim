package com.example.cult_of_tim.cultoftim.service.impl;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.example.cult_of_tim.cultoftim.converter.PromotionConverter;
import com.example.cult_of_tim.cultoftim.converter.PromotionDiscountConverter;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.repositories.PromotionRepository;
import com.example.cult_of_tim.cultoftim.service.FundsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FundsServiceImpl implements FundsService {

    private double currentBalance = 0.0;
    private final UserRepository userRepository;

    @Autowired
    public FundsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }
    public double getCurrentBalance() {
        return currentBalance;
    }

    public void addFunds(double amount) {

        currentBalance += amount;

        System.out.println("Added " + amount + " UAH to the account. Current balance: " + currentBalance + " UAH");
    }
    public void updateUserBalance(UserDTO userDTO) {

        Optional<User> optionalUser = userRepository.findById(userDTO.getUserId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();


            user.setBalance(userDTO.getBalance());


            userRepository.save(user);
        } else {

            throw new EntityNotFoundException("User not found");
        }
    }
}