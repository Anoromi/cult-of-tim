package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<CategoryDto> getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(String name);

    CategoryDto updateCategory(Long id, CategoryDto updatedCategory);

    void deleteCategory(Long id);
}
