package com.example.cult_of_tim.cultoftim.util;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.auth.UserRoles;
import com.example.cult_of_tim.cultoftim.dto.*;
import com.example.cult_of_tim.cultoftim.repositories.PromotionDiscountRepository;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class DefaultTestConfiguration implements InitialDatabaseSeeder {

    final BookService bookService;
    final AuthorService authorService;
    final CategoryService categoryService;
    final PromotionService promotionService;
    final UserService userService;

    final PromotionDiscountRepository promotionDiscountRepository;

    private static final Logger logger
            = LoggerFactory.getLogger(SpringApplication.class);


    @Override
    public void run() throws Exception {
        logger.debug("Debugging hello");
        logger.debug("Debugging hello");
        userService.registerUser("anoromi", "emailexample@gmail.com", "1234Abcd@");
        System.out.println(userService.login("emailexample@gmail.com", "1234Abcd@"));
        try {
            System.out.println(userService.login("emailexample@gmail.com", "wrongPass"));
        }
        catch (IllegalArgumentException e) {

        }

        var adminUser = userService.registerUser("admin", "admin@gmail.com", "1234Abcd@");
        userService.setUserRole(adminUser, UserRoles.ADMIN);



        AuthorDto author = authorService.createAuthor("First Second");
        CategoryDto category = categoryService.createCategory("category");

        BookDto bookDto = new BookDto();
        bookDto.setTitle("title");
        bookDto.setAuthors(List.of(author));
        bookDto.setCategories(List.of(category));
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

        //bookService.addBookFromOpenLibrary("9780545029360", 10);
        //bookService.addBookFromOpenLibrary("9780545029365", 10);
    }
}
