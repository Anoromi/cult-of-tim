package com.example.cult_of_tim.cultoftim.auth;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.validation.Validator;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
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
            authorizeHttpRequests.requestMatchers("/cart/list")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/cart/add/**")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/cart/buy")
                    .permitAll();
            authorizeHttpRequests.requestMatchers("/add/**")
                    .permitAll();




            // POST, PUT, DELETE for admin
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/books/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/authors/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/promotions/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.PUT, "/books/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.PUT, "/authors/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.DELETE, "/books/**")
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
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/books/list").authenticated();
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/books/**").authenticated();
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/books/add")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/books/edit/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/books/delete/**")
                    .hasAuthority(UserRoles.ADMIN);
            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/authors/**").authenticated();

            authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/test/**").permitAll();

            //authorizeHttpRequests.anyRequest().authenticated();
            //authorizeHttpRequests.requestMatchers("")
            //authorizeHttpRequests.requestMatchers(Http)
        });
        http.exceptionHandling((e) -> {
            e.disable();
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
