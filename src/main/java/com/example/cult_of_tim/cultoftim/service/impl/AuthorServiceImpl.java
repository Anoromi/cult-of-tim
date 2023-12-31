package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.converter.AuthorConverter;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.entity.Author;
import com.example.cult_of_tim.cultoftim.external.OpenLibraryAuthor;
import com.example.cult_of_tim.cultoftim.repositories.AuthorRepository;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClientResponseException;
//import reactor.netty.http.client.HttpClient;

import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    @Override
    @Cacheable("authors")
    public Optional<AuthorDto> getAuthorById(Long id) {
        return authorRepository.findById(id).map(authorConverter::toDto);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto createAuthor(String fullName) {
        Author newAuthor = new Author();
        //newAuthor.setFirstName(firstName);
        //newAuthor.setLastName(lastName);
        newAuthor.setFullName(fullName);
        Author savedAuthor = authorRepository.save(newAuthor);
        return authorConverter.toDto(savedAuthor);
    }

    @Override
    public AuthorDto createAuthorFromOpenLibrary(String openLibraryAuthorId) {
        try {
            var optionalAuthor = authorRepository.findByOpenLibraryId(openLibraryAuthorId);
            if (optionalAuthor.isPresent()) {
                return authorConverter.toDto(optionalAuthor.get());
            }
            var result = WebClient.builder()
                    .baseUrl("https://openlibrary.org")
                    .clientConnector(new ReactorClientHttpConnector(
                            HttpClient.create().followRedirect(true)
                    ))
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build().get().uri("/authors/{authorId}.json", openLibraryAuthorId)
                    .retrieve()
                    .toEntity(OpenLibraryAuthor.class)
                    .block();

            assert result != null;
            assert result.getBody() != null;

            Author author;
            var newAuthor = new Author();
            newAuthor.setFullName(result.getBody().name());
            newAuthor.setOpenLibraryId(openLibraryAuthorId);
            newAuthor.setBooks(List.of());
            author = authorRepository.save(newAuthor);

            return authorConverter.toDto(author);
        } catch (WebClientResponseException e) {
            throw new IllegalArgumentException("Illegal author id");
        }

    }

    @Override
    @CachePut(value = "authors", key = "id")
    public AuthorDto updateAuthor(Long id, AuthorDto updatedAuthorDto) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        Author updatedAuthor = authorConverter.toEntity(updatedAuthorDto);

        
        existingAuthor.setFullName(updatedAuthor.getFullName());

        Author savedAuthor = authorRepository.save(existingAuthor);
        return authorConverter.toDto(savedAuthor);
    }

    @Override
    @CacheEvict(value = "authors", key = "id")
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDto> createAuthorDtos(String authors) {
        List<String> authorNames = extractFullNames(authors);
        List<AuthorDto> authorDtoList = getAllAuthors();
        return authorDtoList.stream()
                .filter(author -> authorNames.contains(author.getFullName()))
                .collect(Collectors.toList());
    }
    @Override
    public List<String> extractFullNames(String input) {
        List<String> fullNames = new ArrayList<>();
        Pattern pattern = Pattern.compile("fullName=([^)]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String fullName = matcher.group(1).trim();
            fullNames.add(fullName);
        }

        return fullNames;
    }
}
