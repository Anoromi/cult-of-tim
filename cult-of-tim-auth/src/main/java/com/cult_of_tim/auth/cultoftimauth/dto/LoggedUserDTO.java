package com.cult_of_tim.auth.cultoftimauth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoggedUserDTO {

    private UserDTO user;
    private String token;
}
