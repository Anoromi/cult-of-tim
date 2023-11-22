package com.example.cult_of_tim.cultoftim.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private List<AuthorRequest> authors;
    @NotBlank
    private List<CategoryRequest> categories;
    @NotBlank @Min(0)
    private int quantity;
    private String isbn13;
}
