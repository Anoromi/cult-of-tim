package com.example.cult_of_tim.cultoftim.auth;

import com.cult_of_tim.auth.cultoftimauth.exception.AuthException;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class TimAuthenticationManagerImpl implements TimAuthenticationManager {

    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken token))
            throw new ProviderNotFoundException("");

        try {
            var user = userService.login((String) token.getPrincipal(), (String) token.getCredentials());

            return UsernamePasswordAuthenticationToken.authenticated(user.getUser(), user.getToken(),
                    List.of(new SimpleGrantedAuthority(user.getUser().getRole())));

        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("");
        }
    }


}
