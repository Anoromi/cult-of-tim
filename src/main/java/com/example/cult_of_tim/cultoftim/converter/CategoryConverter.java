package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.controller.request.AuthorRequest;
import com.example.cult_of_tim.cultoftim.controller.request.CategoryRequest;
import com.example.cult_of_tim.cultoftim.dto.AuthorDto;
import com.example.cult_of_tim.cultoftim.dto.CategoryDto;
import com.example.cult_of_tim.cultoftim.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryConverter {

    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public CategoryDto toDto(CategoryRequest category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toEntity(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public CategoryRequest dtoToRequest(CategoryDto categoryDto) {
        return CategoryRequest.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}

