package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.controller.request.BookRequest;
import com.example.cult_of_tim.cultoftim.converter.BookConverter;
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

@Controller
public class BookPageController {

    private final BookService bookService;
    private final BookConverter bookConverter;

    @Autowired
    public BookPageController(BookService bookService, BookConverter bookConverter) {
        this.bookService = bookService;
        this.bookConverter = bookConverter;
    }

    @GetMapping("/books/list")
    public String listBooks(Model model) {
        String role = "Admin";
/*        UserContext userContext = new UserContext();
        Optional<UserDTO> user = userContext.getUser();
        if (user.isPresent()){
            role = user.get().getRole();
        }*/
        model.addAttribute("userRole", role);
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book-list";
    }

/*    @GetMapping("/books/list")
    public String listBooks(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        String role = userDTO.getRole();
        model.addAttribute("userRole", role);
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book-list";
    }*/

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
    public String addBook(@ModelAttribute @Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-add";
        }
        bookService.createBook(bookConverter.toDto(bookRequest));
        return "redirect:/books/list";
    }

    @PostMapping("/books/edit")
    public String editBook(@ModelAttribute @Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-edit";
        }
        Long id = bookRequest.getId();
        bookService.updateBook(id, bookConverter.toDto(bookRequest));
        return "redirect:/books/list";
    }

    @PostMapping("/books/delete")
    public String deleteBook(@ModelAttribute @Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-delete";
        }
        Long id = bookRequest.getId();
        bookService.deleteBook(id);
        return "redirect:/books/list";
    }
}
