package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.converter.CategoryConverter;
import com.example.cult_of_tim.cultoftim.dto.CategoryDto;
import com.example.cult_of_tim.cultoftim.entity.Category;
import com.example.cult_of_tim.cultoftim.repositories.CategoryRepository;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public Optional<CategoryDto> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(categoryConverter::toDto);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto createCategory(String name) {
        Category newCategory = new Category();
        newCategory.setName(name);
        Category savedCategory = categoryRepository.save(newCategory);
        return categoryConverter.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto updatedCategoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Category updatedCategory = categoryConverter.toEntity(updatedCategoryDto);
        updatedCategory.setId(id);

        existingCategory.setName(updatedCategory.getName());

        Category savedCategory = categoryRepository.save(existingCategory);
        return categoryConverter.toDto(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean allCategoriesValid(String categories) {
        String[] categoriesArray = categories.split(", ");

        for (String category : categoriesArray) {
            if (categoryRepository.findByName(category).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
