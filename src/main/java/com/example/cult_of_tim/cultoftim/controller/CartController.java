package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.entity.CartItem;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.repositories.CartItemRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/list")
    public String viewCart(Model model, @AuthenticationPrincipal UserDTO userDTO) {

        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());


        if (user.isPresent()) {
            List<CartItem> cartItems = cartItemRepository.findByUser(user.get());


            model.addAttribute("cartItems", cartItems);
        }

        return "cart/list";
    }

    @GetMapping("/add/{bookId}")
    public String addToCartPage(@PathVariable Long bookId, Model model) {
        Optional<Book> book = bookRepository.findById(bookId);
        model.addAttribute("book", book.orElse(null));
        return "cart/add_to_cart";
    }

    @PostMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, @AuthenticationPrincipal UserDTO userDTO) {

        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());


        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id"));

        if(user.isPresent()) {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user.get());
            cartItem.setBook(book);

            cartItemRepository.save(cartItem);
        }
        return "redirect:/cart/list";
    }
    @PostMapping("/buy")
    public String buyAllBooks(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        if (user.isPresent()) {
            List<CartItem> cartItems = cartItemRepository.findByUser(user.get());

            double totalCost = cartItems.stream().mapToDouble(item -> item.getBook().getPrice()).sum();

            if (userDTO.getBalance() > totalCost) {

                userDTO.setBalance((int) (userDTO.getBalance() - totalCost));

                cartItemRepository.deleteAll(cartItems);

                userRepository.save(user.get());

                return "redirect:/cart/list";
            } else {
                model.addAttribute("error", "Not enough money to complete the purchase.");

                return "cart/list";
            }
        }
        else{
            model.addAttribute("error", "No User");

            return "cart/list";
        }
    }
}