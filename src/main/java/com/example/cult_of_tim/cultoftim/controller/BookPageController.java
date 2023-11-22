package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.example.cult_of_tim.cultoftim.auth.UserContext;
import com.example.cult_of_tim.cultoftim.controller.request.BookRequest;
import com.example.cult_of_tim.cultoftim.converter.BookConverter;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookPageController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookConverter bookConverter;

    @Autowired
    public BookPageController(BookService bookService, BookConverter bookConverter,
                              AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.bookConverter = bookConverter;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @GetMapping("/books/list")
    public String listBooks(Model model) {
        String role = "Default";
        UserContext userContext = new UserContext();
        Optional<UserDTO> user = userContext.getUser();
        if (user.isPresent()){
            role = user.get().getRole();
        }
        model.addAttribute("userRole", role);
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model){
        model.addAttribute("createBookRequest", new BookRequest());
        return "book-add";
    }

    @GetMapping("/books/edit")
    public String showEditBookPage(Model model){
        model.addAttribute("createBookRequest", new BookRequest());
        return "book-edit";
    }

    @GetMapping("/books/delete")
    public String showDeleteBookPage(Model model){
        model.addAttribute("createBookRequest", new BookRequest());
        return "book-delete";
    }

    @PostMapping("/books/add")
    public String addBook(@Valid @RequestParam("title") String bookTitle,
                          @Valid @RequestParam("authors") String authorsInput,
                          @Valid @RequestParam("categories") String categoriesInput,
                          @Valid @ModelAttribute("bookRequest") BookRequest bookRequest,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-add";
        }

        boolean isTitleValid = bookService.isTitleValid(bookTitle);
        boolean areInvalidAuthors = authorService.allAuthorsValid(authorsInput);
        boolean areInvalidCategories = categoryService.allCategoriesValid(categoriesInput);

        if (!isTitleValid){
            return "redirect:/books/add?title";
        }
        else if (!areInvalidAuthors) {
            return "redirect:/books/add?authors";
        }
        else if (!areInvalidCategories) {
            return "redirect:/books/add?categories";
        }

        bookService.createBook(bookConverter.toDto(bookRequest));

        return "redirect:/books/list";
    }

    @PostMapping("/books/edit")
    public String editBook(@Valid @RequestParam("bookId") Long id,
                           @Valid @RequestParam("title") String bookTitle,
                           @Valid @RequestParam("authors") String authorsInput,
                           @Valid @RequestParam("categories") String categoriesInput,
                           @Valid @ModelAttribute("bookRequest") BookRequest bookRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-edit";
        }

        boolean isTitleValid = bookService.isTitleValid(bookTitle);
        boolean areInvalidAuthors = authorService.allAuthorsValid(authorsInput);
        boolean areInvalidCategories = categoryService.allCategoriesValid(categoriesInput);

        if (!isTitleValid){
            return "redirect:/books/edit?title";
        }
        else if (!areInvalidAuthors) {
            return "redirect:/books/edit?authors";
        }
        else if (!areInvalidCategories) {
            return "redirect:/books/edit?categories";
        }

        bookService.updateBook(id, bookConverter.toDto(bookRequest));

        return "redirect:/books/list";
    }

    @PostMapping("/books/delete")
    public String deleteBook(@Valid @RequestParam("title") String title,
                             @Valid @ModelAttribute("bookRequest") BookRequest bookRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-delete";
        }

        List<BookDto> books = bookService.getAllBooks();
        for (BookDto book : books){
            if (book.getTitle().equals(title)) {
                bookService.deleteBook(book.getId());
                return "redirect:/books/list";
            }
        }

        return "redirect:/books/delete?title";
    }
}
