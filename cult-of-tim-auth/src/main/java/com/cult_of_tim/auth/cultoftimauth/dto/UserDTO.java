package com.cult_of_tim.auth.cultoftimauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private UUID userId;

    private String username;

    private String password;

    private String email;

    private String role;

    private Integer balance;

}
