package com.example.cult_of_tim.cultoftim.requestData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUser {
    String usernameOrEmail;
    String password;
}
