package com.example.cult_of_tim.cultoftim.service;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.example.cult_of_tim.cultoftim.dto.PurchaseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FundsService {
    public double getCurrentBalance();

    public void addFunds(double amount);
    public void updateUserBalance(UserDTO userDTO);
}

