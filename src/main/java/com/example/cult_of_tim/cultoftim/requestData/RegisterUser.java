package com.example.cult_of_tim.cultoftim.requestData;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


public class RegisterUser {
    @NotBlank
    public String username;

    @NotBlank
    @Length(min = 8, max=60)
    public String password;

    @NotBlank
    public String email;
}
