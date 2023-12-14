package com.example.cult_of_tim.cultoftim;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.dto.CategoryDto;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import com.example.cult_of_tim.cultoftim.service.FundsService;
import com.example.cult_of_tim.cultoftim.service.PurchaseService;
import org.junit.jupiter.api.Assertions;
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

    @Autowired
    FundsService fundsService;


    @Test
    void basicPurchaseTest() throws AuthException {
        var book = createBook(10);
        var user = registerUser();
        var purchase = purchaseService.purchaseBooks(user, List.of(book));
        Assertions.assertNotNull(purchase);
        Assertions.assertNotNull(purchase.getId());
    }

    @Test
    void otherPurchaseTest() throws AuthException {
        var book = createBook(0);
        var user = registerUser();
        try {
            purchaseService.purchaseBooks(user, List.of(book));
            Assertions.fail("Can't purchase unavailable book");
        } catch (Exception ignored) {

        }

    }

    UUID registerUser() throws AuthException {

        var user = userService.registerUser(UUID.randomUUID() + "testUser", UUID.randomUUID() + "test@user.com", "testPassword1_");
        fundsService.addFunds(user, 1000);
        return user;
    }

    long createBook(int quantity) {
        var book = new BookDto(null, "testBook", 10, List.of(), List.of(createCategory()), quantity, null);
        return bookService.createBook(book).getId();
    }

    CategoryDto createCategory() {
        return categoryService.createCategory("testCategory");
    }
}
