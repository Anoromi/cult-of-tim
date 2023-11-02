package com.example.cult_of_tim.cultoftim.auth;

import com.example.cult_of_tim.cultoftim.controller.request.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;

    private Validator validator;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, Validator validator) {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.validator = validator;

        //setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            var binder = new DataBinder(loginRequest);
            binder.setValidator(validator);
            binder.validate();
            if(binder.getBindingResult().hasErrors())
                throw new BadCredentialsException("");

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(),
                    loginRequest.getPassword()
            ));

        } catch (IOException e) {
            throw new BadCredentialsException("");
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var credentials = (String) authResult.getCredentials();

        response.getWriter().write(credentials);
        response.setStatus(200);
        response.getWriter().flush();


    }
}
