package com.example.cult_of_tim.cultoftim.repositories;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.example.cult_of_tim.cultoftim.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
}
