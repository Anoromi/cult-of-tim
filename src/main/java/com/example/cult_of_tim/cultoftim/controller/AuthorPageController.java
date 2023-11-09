package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.controller.request.CreateAuthorRequest;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
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
public class AuthorPageController {
    private final AuthorService authorService;

    @Autowired
    public AuthorPageController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/authors/list")
    public String listPage(Model model){
        List<AuthorDto> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "author-list";
    }

    @GetMapping("/authors/add")
    public String addPage(Model model){
        model.addAttribute("createAuthorRequest", new CreateAuthorRequest());
        return "author-add";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@ModelAttribute @Valid CreateAuthorRequest createAuthorRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "author-add";
        }
        authorService.createAuthor(createAuthorRequest.getFullName());
        return "redirect:/authorlist";
    }

}
