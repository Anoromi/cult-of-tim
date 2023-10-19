package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.controller.request.AuthorRequest;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    private AuthorDto mapToAuthorDto(AuthorRequest authorRequest){
        return AuthorDto.builder()
                .id(authorRequest.getId())
                .fullName(authorRequest.getFullName())
                .build();
    }

    private AuthorRequest mapToAuthorRequest(AuthorDto authorDto){
        return AuthorRequest.builder()
                .id(authorDto.getId())
                .fullName(authorDto.getFullName())
                .build();
    }

    @PostMapping
    public ResponseEntity<AuthorRequest> createAuthor(@RequestBody AuthorRequest authorRequest) {
        AuthorDto authorDto = mapToAuthorDto(authorRequest);
        AuthorDto createdAuthor = authorService.createAuthor(authorDto.getFullName());
        AuthorRequest response = mapToAuthorRequest(createdAuthor);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping
    public ResponseEntity<AuthorRequest> createAuthorFromOpenLibrary(@RequestBody String openLibrary){
        AuthorDto createdAuthor = authorService.createAuthorFromOpenLibrary(openLibrary);
        AuthorRequest response = mapToAuthorRequest(createdAuthor);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorRequest> getAuthorById(@PathVariable Long id) {
        Optional<AuthorDto> authorDto = authorService.getAuthorById(id);
        if (authorDto.isPresent()) {
            AuthorRequest response = mapToAuthorRequest(authorDto.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<AuthorRequest> getAllAuthors() {
        List<AuthorDto> authorDtos = authorService.getAllAuthors();
        return authorDtos.stream()
                .map(this::mapToAuthorRequest)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorRequest> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest updatedAuthor) {
        Optional<AuthorDto> existingAuthor = authorService.getAuthorById(id);
        if (existingAuthor.isPresent()) {
            AuthorDto updatedAuthorDto = authorService.updateAuthor(id, mapToAuthorDto(updatedAuthor));
            AuthorRequest response = mapToAuthorRequest(updatedAuthorDto);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id) {
        Optional<AuthorDto> existingAuthor = authorService.getAuthorById(id);
        if (existingAuthor.isPresent()) {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAuthorNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
