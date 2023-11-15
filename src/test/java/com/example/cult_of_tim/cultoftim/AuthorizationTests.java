package com.example.cult_of_tim.cultoftim;

import com.example.cult_of_tim.cultoftim.controller.request.CreateAuthorRequest;
import com.example.cult_of_tim.cultoftim.controller.request.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void unauthorizedTest() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
        this.mockMvc.perform(get("/authors")).andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        this.mockMvc.perform(post("/authors")).andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    final String defaultUserUsername = "anoromi";
    final String defaultUserPassword = "1234Abcd@";

    @Test
    void defaultUserTest() throws Exception {
        var token = loginWith(defaultUserUsername, defaultUserPassword);

        this.mockMvc.perform(get("/authors").cookie(new Cookie("token", token))).andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
        this.mockMvc.perform(post("/authors").cookie(new Cookie("token", token))).andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    final String adminUsername = "admin";
    final String adminPassword = "1234Abcd@";

    @Test
    void adminUserTest() throws Exception {

        var token = loginWith(adminUsername, adminPassword);

        this.mockMvc.perform(get("/authors").cookie(new Cookie("token", token))).andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
        this.mockMvc.perform(post("/authors")
                .content(objectMapper.writeValueAsString(new CreateAuthorRequest("Isaac Asimov")))
                .contentType("application/json").cookie(new Cookie("token", token))).andDo(print()).andExpect(status().is(HttpStatus.CREATED.value()));
    }

    String loginWith(String username, String password) throws Exception {
        var result = this.mockMvc.perform(post("/auth/login").contentType("application/json").content(objectMapper.writeValueAsString(new LoginUser(username, password)))).andExpect(status().isOk()).andReturn().getResponse().getCookie("token");
        return result.getValue();
    }

}
