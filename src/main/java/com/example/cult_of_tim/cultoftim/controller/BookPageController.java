package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.example.cult_of_tim.cultoftim.auth.UserContext;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.dto.CategoryDto;
import com.example.cult_of_tim.cultoftim.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BookPageController {

    private final BookService bookService;
    private final PromotionService promotionService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping("/books/list")
    public String listBooks(Model model) {
        String role = "Default";
        UserContext userContext = new UserContext();
        Optional<UserDTO> user = userContext.getUser();
        if (user.isPresent()) {
            role = user.get().getRole();
        }
        model.addAttribute("userRole", role);
        model.addAttribute("books", bookService.getAllBooks());
        return "book-list";
    }

    @GetMapping("/books/add")
    public String addBookPage(Model model) {
        model.addAttribute("createBookDto", new BookDto());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("selectedAuthors", new ArrayList<AuthorDto>());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategories", new ArrayList<CategoryDto>());
        return "book-add";
    }

    @PostMapping("/books/add")
    public String addBook(@Valid @RequestParam("title") String bookTitle,
                          @Valid @RequestParam("selectedAuthors") String authors,
                          @Valid @RequestParam("selectedCategories") String categories,
                          @Valid @RequestParam("price") Integer price,
                          @Valid @RequestParam("quantity") int bookQuantity,
                          @Valid @ModelAttribute BookDto createBookDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-add";
        }

        boolean bookExists = bookService.bookExists(bookTitle);

        if (bookExists) {
            return "redirect:/books/add?title";
        } else if (price < 0) {
            return "redirect:/books/add?price";
        } else if (bookQuantity < 0) {
            return "redirect:/books/add?quantity";
        }

        createBookDto.setTitle(bookTitle);
        createBookDto.setAuthors(authorService.createAuthorDtos(authors));
        createBookDto.setCategories(categoryService.createCategoryDtos(categories));
        createBookDto.setPrice(price);
        createBookDto.setQuantity(bookQuantity);

        bookService.createBook(createBookDto);

        return "redirect:/books/list";
    }

    @GetMapping("/books/edit/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        BookDto bookDto = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("selectedAuthors", bookDto.getAuthors());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategories", bookDto.getCategories());
        return "book-edit";
    }

    @PostMapping("/books/edit/{id}")
    public String editBook(@PathVariable Long id,
                           @Valid @RequestParam("title") String bookTitle,
                           @Valid @RequestParam("selectedAuthors") String authors,
                           @Valid @RequestParam("selectedCategories") String categories,
                           @Valid @RequestParam("price") Integer price,
                           @Valid @RequestParam("quantity") int bookQuantity,
                           @Valid @ModelAttribute("updateBookDto") BookDto updatedBookDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-edit";
        }

        boolean bookExists = bookService.bookExists(bookTitle);
        boolean bookTitleMatchesOld = bookService.oldTitleMatchNew(id, bookTitle);

        if (bookExists && !bookTitleMatchesOld) {
            return "redirect:/books/edit/" + id + "?title";
        } else if (price < 0) {
            return "redirect:/books/edit/" + id + "?price";
        } else if (bookQuantity < 0) {
            return "redirect:/books/edit/" + id + "?quantity";
        }

        updatedBookDto.setTitle(bookTitle);
        updatedBookDto.setAuthors(authorService.createAuthorDtos(authors));
        updatedBookDto.setCategories(categoryService.createCategoryDtos(categories));
        updatedBookDto.setPrice(price);
        updatedBookDto.setQuantity(bookQuantity);

        bookService.updateBook(id, updatedBookDto);

        return "redirect:/books/list";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books/list";
    }


    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, @AuthenticationPrincipal UserDTO userDTO) {

        cartService.addToCart(userDTO, bookId);


        return "redirect:/cart/list";
    }
}
