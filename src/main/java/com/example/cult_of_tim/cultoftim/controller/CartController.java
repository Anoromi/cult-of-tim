package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.entity.CartItem;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.repositories.CartItemRepository;
import com.example.cult_of_tim.cultoftim.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final UserService userService;
    private final CartItemService cartItemService;

    @Autowired
    public CartController(UserService userService, CartItemService cartItemService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
    }


    @GetMapping("/list")
    public String viewCart(Model model, @AuthenticationPrincipal UserDTO userDTO) {

        Optional<User> user = userService.getUserById(userDTO.getUserId());


        if (user.isPresent()) {
            List<CartItem> cartItems = cartItemService.getCartItemsByUser(user.get());
            model.addAttribute("cartItems", cartItems);
        }

        return "cart/list";
    }




    @PostMapping("/buy")
    public String buyAllBooks(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        Optional<User> user = userService.getUserById(userDTO.getUserId());
        if (user.isPresent()) {
            User realUser = cartItemService.buyAllBooks(user.get());
            if(realUser!=null) {
                userService.updateUser(userDTO.getEmail(), realUser);

                return "cart/list";
            }
            else {
                model.addAttribute("error", "Not enough money to complete the purchase.");

                return "cart/list";
            }
        }
        else{
            model.addAttribute("error", "No User");

            return "cart/list";
        }
    }
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        Optional<User> user = userService.getUserById(userDTO.getUserId());
        if (user.isPresent()) {
            cartItemService.clearAllBooks(user.get());
            return "redirect:/cart/list";
        } else {
            model.addAttribute("error", "No User");
            return "cart/list";
        }
    }
}