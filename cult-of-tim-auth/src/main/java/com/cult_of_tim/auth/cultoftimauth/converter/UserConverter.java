package com.cult_of_tim.auth.cultoftimauth.converter;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.cult_of_tim.auth.cultoftimauth.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {


    public User toEntity(UserDTO user) {
        return User.builder()
                .userId(user.getUserId())
                .role(user.getRole())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .balance(user.getBalance())
                .build();

    }


    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .role(user.getRole())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .balance(user.getBalance())
                .build();
    }
}
