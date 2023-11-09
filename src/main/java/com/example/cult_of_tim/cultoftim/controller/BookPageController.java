package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.example.cult_of_tim.cultoftim.auth.UserContext;
import com.example.cult_of_tim.cultoftim.controller.request.BookRequest;
import com.example.cult_of_tim.cultoftim.dto.BookDto;
import com.example.cult_of_tim.cultoftim.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Optional;

@Controller
public class BookPageController {

    @Autowired
    private BookService bookService;

    @GetMapping("/booklist")
    public String listBooks(Model model) {
        UserContext userContext = new UserContext();
        Optional<UserDTO> user = userContext.getUser();
        String role = "Default";
        if (user.isPresent()){
            role = user.get().getRole();
        }
        model.addAttribute("userRole", role);
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "/bookslist";
    }

    @GetMapping("/booklist")
    public String listBooksPage(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/booklist/add")
    public String showAddBookForm(Model model){
        model.addAttribute("createBookRequest", new BookRequest());
        return "book-add";
    }

    @GetMapping("/booklist/edit")
    public String showEditBookPage(Model model){
        model.addAttribute("createBookRequest", new BookRequest());
        return "book-edit";
    }

    @GetMapping("/booklist/delete")
    public String showDeleteBookPage(Model model){
        model.addAttribute("createBookRequest", new BookRequest());
        return "book-delete";
    }

    @PostMapping("/booklist/add")
    public String addBook(@ModelAttribute @Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-add";
        }
        // bookService.createBook(bookRequest); bookRequest to bookDto converted needed
        return "redirect:/booklist";
    }

    @PostMapping("/booklist/edit")
    public String editBook(@ModelAttribute @Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-edit";
        }
        Long id = bookRequest.getId();
        //bookService.updateBook(id, bookRequest.getFullName()); bookRequest to bookDto converted needed
        return "redirect:/booklist";
    }

    @PostMapping("/booklist/delete")
    public String deleteBook(@ModelAttribute @Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-delete";
        }
        Long id = bookRequest.getId();
        bookService.deleteBook(id);
        return "redirect:/booklist";
    }
}
