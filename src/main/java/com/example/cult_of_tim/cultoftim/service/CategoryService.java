package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> getCategoryById(Long id);

    List<Category> getAllCategories();

    Long createCategory(String name);

    Category updateCategory(Long id, Category updatedCategory);

    void deleteCategory(Long id);
}
