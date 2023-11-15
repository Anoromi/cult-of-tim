package com.example.cult_of_tim.cultoftim.auth;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.validation.Validator;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
@EnableWebSecurity
public class TimWebSecurity {

    //@Bean
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //    return http.build();
    //}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, Validator validator, TimAuthenticationManager authenticationManager,
                                                   UserService userService, SpringTemplateEngine templateEngine) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new TokenAuthenticationFilter(authenticationManager, validator, templateEngine),
                        BasicAuthenticationFilter.class)
                .addFilterBefore(
                        new TokenAuthorizationFilter(authenticationManager, userService),
                        BasicAuthenticationFilter.class);

        //http.formLogin(form -> form)
        http.authorizeHttpRequests((authorizeHttpRequests) -> {
            authorizeHttpRequests.requestMatchers("/").permitAll();
            authorizeHttpRequests.requestMatchers("/auth/signup")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/auth/login")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/login")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/register")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/addFunds")
                    .permitAll();

            // POST, PUT, DELETE for admin
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/books")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/authors/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/promotions/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.PUT, "/books")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.PUT, "/authors/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.DELETE, "/books")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.DELETE, "/authors/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.DELETE, "/promotions/**")
                    .hasAuthority(UserRoles.ADMIN);

            authorizeHttpRequests.requestMatchers("/css/**")
                    .permitAll();

            // GET for authorized
//            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/promotion/**").authenticated();
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/promotions/**").authenticated();
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/books").authenticated();
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/authors/**").authenticated();

            //authorizeHttpRequests.anyRequest().authenticated();
            //authorizeHttpRequests.requestMatchers("")
            //authorizeHttpRequests.requestMatchers(Http)
        });

        // TODO fix forwarding
        /*http.formLogin(
                form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/books")
                        .permitAll()
        );*/

        return http.build();
    }
}
