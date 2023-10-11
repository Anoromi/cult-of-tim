package com.example.cult_of_tim.cultoftim;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.example.cult_of_tim.cultoftim.dto.*;
import com.example.cult_of_tim.cultoftim.repositories.PromotionDiscountRepository;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories()
@EntityScan()
public class CultOfTimApplication implements CommandLineRunner {

    final BookService bookService;
    final AuthorService authorService;
    final CategoryService categoryService;
    final PromotionService promotionService;
    final UserService userService;

    final PromotionDiscountRepository promotionDiscountRepository;

        private static final Logger logger
      = LoggerFactory.getLogger(SpringApplication.class);


    @Autowired
    public CultOfTimApplication(BookService bookService, AuthorService authorService, CategoryService categoryService, PromotionService promotionService, UserService userService, PromotionDiscountRepository promotionDiscountRepository) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.promotionService = promotionService;
        this.userService = userService;
        this.promotionDiscountRepository = promotionDiscountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CultOfTimApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.debug("Debugging hello");
        userService.registerUser("anoromi", "emailexample@gmail.com", "1234Abcd@");
        System.out.println(userService.login("emailexample@gmail.com", "1234Abcd@"));
        System.out.println(userService.login("emailexample@gmail.com", "wrongPass"));
        AuthorDto author = authorService.createAuthor("First", "Second");
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
    }
}
