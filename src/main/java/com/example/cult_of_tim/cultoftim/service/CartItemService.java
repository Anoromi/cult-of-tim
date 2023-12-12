package com.example.cult_of_tim.cultoftim.service;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.entity.CartItem;

import java.util.List;
import java.util.Optional;

import java.util.List;

public interface CartItemService {
    List<CartItem> getCartItemsByUser(User user);
    User buyAllBooks(User user);

    CartItem bookExistsCartItem(Long id, User user);

    CartItem updateCartItem(Long id, CartItem updatedCartItem);

    void clearAllBooks(User user);
}