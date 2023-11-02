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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        var header = request.getHeader(AUTHORIZATION_HEADER);
        if(header != null) {

            var token = header.replace(HEADER_PREFIX, "");


            var user = userService.getUserByToken(token);

            if (user.isPresent()) {
                SecurityContextHolder.getContext().setAuthentication(
                        UsernamePasswordAuthenticationToken.authenticated(
                                user, null, List.of(new SimpleGrantedAuthority(user.get().getRole()))
                        )
                );
            }
        }

        chain.doFilter(request, response);


    }
}
