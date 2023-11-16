package com.example.cult_of_tim.cultoftim;

import com.example.cult_of_tim.cultoftim.controller.AuthorController;
import com.example.cult_of_tim.cultoftim.controller.request.AuthorRequest;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.service.AuthorService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerTests {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAuthors() throws Exception {
        var authorList = List.of(new AuthorDto(0L, "Isacc Asimov"));
        Mockito.when(authorService.getAllAuthors()).thenReturn(authorList);


        var result = mockMvc.perform(get("/authors")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        var objectList = objectMapper.readValue(result, new TypeReference<List<AuthorRequest>>() {
        });

        Assertions.assertThat(objectList).contains(new AuthorRequest(0L, "Isacc Asimov")).hasSize(1);

    }


}
