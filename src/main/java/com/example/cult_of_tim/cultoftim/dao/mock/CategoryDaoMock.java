package com.example.cult_of_tim.cultoftim.dao.mock;

import com.example.cult_of_tim.cultoftim.dao.CategoryDao;
import com.example.cult_of_tim.cultoftim.models.Category;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryDaoMock implements CategoryDao {

    private final Map<Long, Category> categoryMap = new HashMap<>();
    private Long nextCategoryId = 1L;

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return Optional.ofNullable(categoryMap.get(id));
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryMap.values());
    }

    @Override
    public void createCategory(Category category) {
        category.setId(nextCategoryId++);
        categoryMap.put(category.getId(), category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMap.put(category.getId(), category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryMap.remove(id);
    }
}
