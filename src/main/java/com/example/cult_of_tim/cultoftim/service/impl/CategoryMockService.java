package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.dao.CategoryDao;
import com.example.cult_of_tim.cultoftim.models.Category;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryMockService implements CategoryService {

    private /*final*/ CategoryDao categoryDao;

    // better
    /*
    @Autowired
    public CategoryMockService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }*/

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryDao.getCategoryById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Long createCategory(String name) {
        Category newCategory = new Category();
        newCategory.setName(name);
        return categoryDao.createCategory(newCategory);
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {
        updatedCategory.setId(id);
        return categoryDao.updateCategory(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryDao.deleteCategoryById(id);
    }
}
