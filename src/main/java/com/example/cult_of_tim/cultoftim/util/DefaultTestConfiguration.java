package com.example.cult_of_tim.cultoftim.util;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.auth.UserRoles;
import com.example.cult_of_tim.cultoftim.dto.*;
import com.example.cult_of_tim.cultoftim.repositories.PromotionDiscountRepository;
import com.example.cult_of_tim.cultoftim.service.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class DefaultTestConfiguration implements InitialDatabaseSeeder {

    final BookService bookService;
    final AuthorService authorService;
    final CategoryService categoryService;
    final PromotionService promotionService;
    final UserService userService;
    final PurchaseService purchaseService;
    final FundsService fundsService;

    final PromotionDiscountRepository promotionDiscountRepository;

    private static final Logger logger
            = LoggerFactory.getLogger(SpringApplication.class);


    @Override
    public void run() throws Exception {
        logger.debug("Debugging hello");
        var defaultUser = userService.registerUser("anoromi", "emailexample@gmail.com", "1234Abcd@");
        fundsService.addFunds(defaultUser, 1000);
        System.out.println(userService.login("emailexample@gmail.com", "1234Abcd@"));
        try {
            System.out.println(userService.login("emailexample@gmail.com", "wrongPass"));
        } catch (IllegalArgumentException e) {

        }

        var adminUser = userService.registerUser("admin", "admin@gmail.com", "1234Abcd@");
        userService.setUserRole(adminUser, UserRoles.ADMIN);


        AuthorDto author = authorService.createAuthor("First Second");
        CategoryDto category = categoryService.createCategory("category");

        BookDto bookDto = new BookDto();
        bookDto.setTitle("title");
        bookDto.setAuthors(List.of(author));
        bookDto.setCategories(List.of(category));
        bookDto.setQuantity(2);
        bookDto.setPrice(100);
        BookDto book = bookService.createBook(bookDto);

        PromotionDto promdto = new PromotionDto();
        promdto.setStartDate(LocalDateTime.now());
        promdto.setEndDate(LocalDateTime.now().plusDays(2));
        PromotionDto promotion = promotionService.createPromotion(promdto);

        PromotionDiscountDto promotionDiscountDto = new PromotionDiscountDto();
        promotionDiscountDto.setPromotionId(promotion.getId());
        promotionDiscountDto.setBookId(book.getId());
        promotionDiscountDto.setDiscountPercentage(50);
        promotionService.addBookWithDiscountToPromotion(promotionDiscountDto);

        purchaseService.purchaseBooks(defaultUser, List.of(book.getId()));

        Date date = new Date();
        date = Date.from(date.toInstant().plus(10, ChronoUnit.SECONDS));


        //bookService.addBookFromOpenLibrary("9780545029360", 10);
        //bookService.addBookFromOpenLibrary("9780545029365", 10);
    }
}
