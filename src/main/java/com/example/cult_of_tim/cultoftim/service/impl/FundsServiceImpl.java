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
import java.util.UUID;

@Service
public class FundsServiceImpl implements FundsService {

    private final UserRepository userRepository;

    @Autowired
    public FundsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public void addFunds(UUID userId, int amount) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return;
        optionalUser.get().setBalance(optionalUser.get().getBalance() + amount);
        userRepository.save(optionalUser.get());
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