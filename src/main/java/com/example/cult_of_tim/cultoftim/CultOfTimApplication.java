package com.example.cult_of_tim.cultoftim;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.models.*;
import com.example.cult_of_tim.cultoftim.repositories.AuthorRepository;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableJpaRepositories()
@EntityScan()
public class CultOfTimApplication implements CommandLineRunner {

    final BookService bookService;
    final AuthorService authorService;
    final CategoryService categoryService;
    final PromotionService promotionService;
    final UserService userService;

    @Autowired
    AuthorRepository repo;


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
        //var author = new Author();
        //author.setName("Hello");
        //repo.save(author);

        //System.out.println(repo.getAuthorsByName("Hello").getId());

        UUID userId = userService.registerUser("anoromi", "emailexample@gmail.com", "1234Abcd@");
        System.out.println(userService.login("emailexample@gmail.com", "1234Abcd@"));
        System.out.println(userService.login("emailexample@gmail.com", "wrongPass"));
        Author author = authorService.createAuthor("First", "Second");
        Category category = categoryService.createCategory("category");
        Book book = bookService.createBook("title", List.of(author), List.of(category));
        Promotion promdto = new Promotion();
        promdto.setStartDate(LocalDateTime.now());
        promdto.setEndDate(LocalDateTime.now().plusDays(2));
        PromotionDiscount promotionDiscount = new PromotionDiscount();
        promotionDiscount.setDiscountPercentage(50);
        promotionDiscount.setBook(book);
        promdto.setDiscounts(List.of(promotionDiscount));
        Promotion promotion = promotionService.createPromotion(promdto);

    }
}
