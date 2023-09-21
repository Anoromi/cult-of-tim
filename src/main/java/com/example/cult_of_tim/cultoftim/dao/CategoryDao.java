package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    Optional<Category> getCategoryById(Long id);

    List<Category> getAllCategories();

    void createCategory(Category category);

    void updateCategory(Category category);

    void deleteCategoryById(Long id);
}