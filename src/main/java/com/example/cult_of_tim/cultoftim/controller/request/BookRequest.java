package com.example.cult_of_tim.cultoftim.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private List<AuthorRequest> authors;
    @NotEmpty
    private List<CategoryRequest> categories;
    @NotNull @Min(0)
    private int quantity;
    @NotNull
    private int price;
    private String isbn13;
}
