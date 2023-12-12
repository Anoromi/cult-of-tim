package com.example.cult_of_tim.cultoftim.service.impl;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.converter.BookConverter;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.entity.CartItem;
import com.example.cult_of_tim.cultoftim.repositories.CartItemRepository;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    private BookService bookService;
    @Autowired
    private BookConverter bookConverter;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> getCartItemsByUser(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Override
    public User buyAllBooks(User user) {
        List<CartItem> cartItems = getCartItemsByUser(user);
        double totalCost = cartItems.stream().mapToDouble(item -> item.getBook().getPrice()).sum();
        if (user.getBalance() >= totalCost) {



            for (CartItem cartItem : cartItems) {
                BookDto updatedBookDto = bookConverter.toDto(cartItem.getBook());
                Book book = cartItem.getBook();

                if (book.getQuantity() - cartItem.getQuantity() < 0) {
                    return null;
                }

                updatedBookDto.setQuantity(book.getQuantity() - cartItem.getQuantity());
                bookService.updateBook(book.getId(), updatedBookDto);
            }
            user.setBalance((int) (user.getBalance() - totalCost));
            cartItemRepository.deleteAll(cartItems);
            return user;
        } else {
            return null;
        }
    }
    @Override
    public CartItem bookExistsCartItem(Long id, User user) {
        List<CartItem> cartItems = getCartItemsByUser(user);
        if (bookService.getBookById(id).isPresent()) {
            for (CartItem cartItem : cartItems) {
                if(cartItem.getBook().getTitle().equals(bookService.getBookById(id).get().getTitle())){
                    return cartItem;
                }
            }

        }
        return null;

    }

    @Override
    public CartItem updateCartItem(Long id, CartItem updatedCartItem) {
        Optional<CartItem> existingCartItemOptional = cartItemRepository.findById(id);

        if (existingCartItemOptional.isPresent()) {
            CartItem existingCartItem = existingCartItemOptional.get();
            existingCartItem.setQuantity(updatedCartItem.getQuantity());
            return cartItemRepository.save(existingCartItem);
        }
        return null;
    }

    @Override
    public void clearAllBooks(User user){
        List<CartItem> cartItems = getCartItemsByUser(user);
        cartItemRepository.deleteAll(cartItems);

    }
}