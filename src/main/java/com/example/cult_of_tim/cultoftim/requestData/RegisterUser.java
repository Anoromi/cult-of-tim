package com.example.cult_of_tim.cultoftim.requestData;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUser {
    @NotBlank
    String username;

    @NotBlank
    @Min(8)
    String password;

    @NotBlank
    String email;
}
