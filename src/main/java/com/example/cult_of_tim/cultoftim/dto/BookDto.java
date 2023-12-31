package com.example.cult_of_tim.cultoftim.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String title;
    private Integer price;
    private List<AuthorDto> authors;
    private List<CategoryDto> categories;
    private int quantity;
    private String isbn13;

    //private boolean isAvailable()
}

