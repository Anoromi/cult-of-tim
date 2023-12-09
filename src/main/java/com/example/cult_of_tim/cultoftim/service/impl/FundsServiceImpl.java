package com.example.cult_of_tim.cultoftim.service.impl;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
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

    @Autowired
    private UserService userService;




    public void updateUserBalance(UserDTO userDTO) {

        Optional<User> optionalUser = userService.getUserById(userDTO.getUserId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();


            user.setBalance(userDTO.getBalance());

            userService.updateUser(user.getEmail(), user);



        } else {

            throw new EntityNotFoundException("User not found");
        }
    }
}