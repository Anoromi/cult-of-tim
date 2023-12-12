package com.example.cult_of_tim.cultoftim.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAuthorRequest {
    @NotBlank
    private String fullName;
}
