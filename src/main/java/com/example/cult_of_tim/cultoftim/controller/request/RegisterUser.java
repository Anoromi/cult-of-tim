package com.example.cult_of_tim.cultoftim.controller.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUser {
    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8, max=60)
    private String password;

    @NotBlank
    private String email;
}
