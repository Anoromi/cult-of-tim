package com.example.cult_of_tim.cultoftim.auth;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import jakarta.servlet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.validation.Validator;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class TimWebSecurity {

    //@Bean
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //    return http.build();
    //}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, Validator validator, TimAuthenticationManager authenticationManager,
                                                   UserService userService) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new TokenAuthenticationFilter(authenticationManager, validator),
                        BasicAuthenticationFilter.class)
                .addFilterBefore(
                        new TokenAuthorizationFilter(authenticationManager, userService),
                        BasicAuthenticationFilter.class);

        http.authorizeHttpRequests((authorizeHttpRequests) -> {
            authorizeHttpRequests.requestMatchers("/auth/signup")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/auth/login")
                    .permitAll();
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/books")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/authors/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/books").authenticated();
            //authorizeHttpRequests.anyRequest().authenticated();
            //authorizeHttpRequests.requestMatchers("")
            //authorizeHttpRequests.requestMatchers(Http)
        });


        //http.addFilter(new Filter() {
        //    @Override
        //    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //
        //    }
        //});

        return http.build();
    }
}
