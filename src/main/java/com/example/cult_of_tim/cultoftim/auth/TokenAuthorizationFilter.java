package com.example.cult_of_tim.cultoftim.auth;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    UserService userService;

    public TokenAuthorizationFilter(AuthenticationManager authenticationManager,
                                    UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    private static final String HEADER_PREFIX = "Bearer ";
    private static final String TOKEN_COOKIE = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        var cookies = request.getCookies();
        var header = request.getHeader(AUTHORIZATION_HEADER);
        if (cookies == null) {
            chain.doFilter(request, response);
            return;
        }

        var token = Arrays.stream(cookies).filter(c -> c.getName().equals(TOKEN_COOKIE)).findAny();

        if (token.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }


        var user = userService.getUserByToken(token.get().getValue());

        if (user.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(
                    UsernamePasswordAuthenticationToken.authenticated(
                            user, null, List.of(new SimpleGrantedAuthority(user.get().getRole()))
                    )
            );
        }


    }
}
