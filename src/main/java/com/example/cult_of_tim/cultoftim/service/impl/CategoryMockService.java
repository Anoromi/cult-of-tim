package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.models.Category;
import com.example.cult_of_tim.cultoftim.repositories.CategoryRepository;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryMockService implements CategoryService {

    private /*final*/ CategoryRepository categoryRepository;

    // better
    /*
    @Autowired
    public CategoryMockService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }*/

    @Autowired
    public void setCategoryDao(CategoryRepository categoryDao) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(String name) {
        Category newCategory = new Category();
        newCategory.setName(name);
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {
        updatedCategory.setId(id);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
