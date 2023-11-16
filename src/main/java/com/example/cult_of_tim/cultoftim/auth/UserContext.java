package com.example.cult_of_tim.cultoftim.auth;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserContext {

    public Optional<UserDTO> getUser() {
        var context = SecurityContextHolder.getContext().getAuthentication();
        if (context instanceof UsernamePasswordAuthenticationToken token) {
            return Optional.ofNullable((UserDTO) token.getPrincipal());
        }

        return Optional.empty();
    }
}
