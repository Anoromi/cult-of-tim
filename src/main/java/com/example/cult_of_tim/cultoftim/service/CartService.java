package com.example.cult_of_tim.cultoftim.service;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;

public interface CartService {


    void addToCart(UserDTO userDTO, long bookId);
}
