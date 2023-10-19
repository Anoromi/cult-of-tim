package com.example.cult_of_tim.cultoftim.controller.request;

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
    private Long id;
    private String title;
    private List<AuthorRequest> authors;
    private List<CategoryRequest> categories;
    private int quantity;
    private String isbn13;
}
