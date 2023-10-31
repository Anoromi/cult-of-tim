package com.example.cult_of_tim.cultoftim.auth;

import jakarta.servlet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class TimWebSecurity {

    //@Bean
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //    return http.build();
    //}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((authorizeHttpRequests) -> {
            authorizeHttpRequests.requestMatchers("/auth/signup")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/auth/login")
                    .permitAll();
            authorizeHttpRequests.anyRequest().authenticated();
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
