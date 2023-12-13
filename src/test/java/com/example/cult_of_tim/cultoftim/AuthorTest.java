package com.example.cult_of_tim.cultoftim;

import com.example.cult_of_tim.cultoftim.converter.AuthorConverter;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.entity.Author;
import com.example.cult_of_tim.cultoftim.repositories.AuthorRepository;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import com.example.cult_of_tim.cultoftim.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {AuthorServiceImpl.class, AuthorRepository.class, AuthorConverter.class})
public class AuthorTest {

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setFullName("John Doe");
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Optional<AuthorDto> result = authorService.getAuthorById(authorId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(author.getId(), result.get().getId());
        Assertions.assertEquals(author.getFullName(), result.get().getFullName());
    }

    @Test
    void testGetAllAuthors() {
        // Setup mock data
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "Author1", null, null));
        authors.add(new Author(2L, "Author2", null, null));
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDto> result = authorService.getAllAuthors();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Author1", result.get(0).getFullName());
        Assertions.assertEquals("Author2", result.get(1).getFullName());
    }

    @Test
    void testCreateAuthor() {
        String fullName = "New Author";
        Author savedAuthor = new Author();
        savedAuthor.setId(3L);
        savedAuthor.setFullName(fullName);
        Mockito.when(authorRepository.save(Mockito.any())).thenReturn(savedAuthor);

        AuthorDto result = authorService.createAuthor(fullName);

        Assertions.assertEquals(fullName, result.getFullName());
        Assertions.assertNotNull(result.getId());
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorDto updatedAuthorDto = new AuthorDto();
        updatedAuthorDto.setId(authorId);
        updatedAuthorDto.setFullName("Updated Author");

        Author existingAuthor = new Author();
        existingAuthor.setId(authorId);
        existingAuthor.setFullName("Old Author");

        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        existingAuthor.setFullName(updatedAuthorDto.getFullName());

        Mockito.when(authorRepository.save(Mockito.any())).thenReturn(existingAuthor);

        AuthorDto result = authorService.updateAuthor(authorId, updatedAuthorDto);

        Assertions.assertEquals(updatedAuthorDto.getId(), result.getId());
        Assertions.assertEquals(updatedAuthorDto.getFullName(), result.getFullName());
    }
}