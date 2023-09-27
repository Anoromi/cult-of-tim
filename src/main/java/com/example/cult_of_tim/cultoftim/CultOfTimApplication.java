package com.example.cult_of_tim.cultoftim;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.cult_of_tim.auth.cultoftimauth.util.PasswordEncrypter;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.example.cult_of_tim.cultoftim.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.cult_of_tim.cultoftim", "com.cult_of_tim.auth.cultoftimauth"})
public class CultOfTimApplication implements CommandLineRunner {

    final BookService bookService;
    final AuthorService authorService;
    final CategoryService categoryService;
    final PromotionService promotionService;
    final UserService userService;


    @Autowired
    public CultOfTimApplication(BookService bookService, AuthorService authorService, CategoryService categoryService, PromotionService promotionService, UserService userService) {

        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.promotionService = promotionService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CultOfTimApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //Long userId = userService.registerUser("emailexample@gmail.com", "1234Abcd@");
        //System.out.println(userService.login("emailexample@gmail.com", "1234Abcd@"));
        //System.out.println(userService.login("emailexample@gmail.com", "wrongPass"));
    }
}
