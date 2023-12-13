package com.example.cult_of_tim.cultoftim;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.dto.CategoryDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import com.example.cult_of_tim.cultoftim.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class PurchaseTest {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;


    @Test
    void basicPurchaseTest() {
        createBook();
    }


    UUID registerUser() throws AuthException {
        return userService.registerUser("testUser", "test@user.com", "testPassword");
    }

    long createBook() {
        var book = new BookDto(null, "testBook", 10, List.of(), List.of(createCategory()), 10, null);
        return bookService.createBook(book).getId();
    }

    CategoryDto createCategory() {
        var category = categoryService.createCategory("testCategory");
        return category;
    }
}
