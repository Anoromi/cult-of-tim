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
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/authors/list";
    }

    @GetMapping("/authors/edit/{id}")
    public String editPage(@PathVariable Long id, Model model){
        AuthorDto authorDto = authorService.getAuthorById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("author", authorDto);
        return "author-edit";
    }
    @PostMapping("/authors/edit/{id}")
    public String editAuthor(@PathVariable Long id, @ModelAttribute @Valid CreateAuthorRequest createAuthorRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "author-edit";
        }
        AuthorDto updatedAuthorDto = new AuthorDto();
        updatedAuthorDto.setFullName(createAuthorRequest.getFullName());

        authorService.updateAuthor(id, updatedAuthorDto);
        return "redirect:/authors/list";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors/list";
    }

}
