package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CategoryService {

    Optional<CategoryDto> getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(String name);

    CategoryDto updateCategory(Long id, CategoryDto updatedCategory);

    void deleteCategory(Long id);

    List<CategoryDto> createCategoryDtos(String categories);

    List<String> extractNames(String input);
}
