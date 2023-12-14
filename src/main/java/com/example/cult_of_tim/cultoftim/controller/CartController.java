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
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final UserService userService;
    private final CartItemService cartItemService;

    private CartItemRepository cartItemRepository;

    private PromotionService promotionService;

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
            List<CartItem> cartItems = cartItemRepository.findByUser(user.get());

            double totalCost = cartItems.stream()
                    .mapToDouble(item ->
                            promotionService.getPrice(item.getBook().getId(), item.getBook().getPrice())*item.getQuantity()
                    )
                    .sum();
            User realUser = user.get();
            if (realUser.getBalance() >= totalCost) {


                realUser.setBalance((int) (userDTO.getBalance() - totalCost));


                cartItemRepository.deleteAll(cartItems);


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