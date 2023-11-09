package com.example.cult_of_tim.cultoftim.auth;

import com.example.cult_of_tim.cultoftim.controller.request.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.accept.HeaderContentTypeResolver;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;

    private final Validator validator;
    SpringTemplateEngine templateEngine;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, Validator validator, SpringTemplateEngine templateEngine) {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.validator = validator;
        this.templateEngine = templateEngine;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String contentType = request.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                var loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
                var binder = new DataBinder(loginRequest);
                binder.setValidator(validator);
                binder.validate();
                if (binder.getBindingResult().hasErrors())
                    throw new BadCredentialsException("");

                return getAuthentication(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
            } else {
                String usernameOrEmail = request.getParameter("usernameOrEmail");
                String password = request.getParameter("password");
                return getAuthentication(usernameOrEmail, password);
            }
        } catch (IOException e) {
            throw new BadCredentialsException("");
        }
    }

    private Authentication getAuthentication(String usernameOrEmail, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                usernameOrEmail,
                password
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var credentials = (String) authResult.getCredentials();

        var tokenCookie = new Cookie("token", credentials);
        tokenCookie.setMaxAge(60 * 60 * 24);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
        response.setStatus(200);
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("access-control-expose-headers",  "Set-Cookie");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        var url = request.getRequestURL().toString();
        var uri = request.getRequestURI();
        response.setHeader("Access-Control-Allow-Origin", url.replace(uri, ""));

        //response.sendRedirect("/");
        var template = templateEngine.process("success", new Context());
        var writer = response.getWriter();
        writer.write(template);
        writer.flush();

        //response.sendRedirect("/");
        //var writer = response.getWriter();

        //writer.write("""
        //        <!DOCTYPE html>
        //        <html>
        //        <head><meta http-equiv="refresh" content="0; url='/'"></head>
        //        <body></body>
        //        </html>
        //                        """);

        //writer.flush();

    }
}
