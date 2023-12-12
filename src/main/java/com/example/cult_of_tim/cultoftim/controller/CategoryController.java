package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.controller.request.CategoryRequest;
import com.example.cult_of_tim.cultoftim.dto.CategoryDto;
import com.example.cult_of_tim.cultoftim.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryRequest> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return categories.stream()
                .map(this::mapToCategoryRequest)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRequest> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDto> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(mapToCategoryRequest(category.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody())
    public ResponseEntity<CategoryRequest> createCategory(@RequestBody String categoryName) {
        CategoryDto createdCategory = categoryService.createCategory(categoryName);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToCategoryRequest(createdCategory));
    }

    @PutMapping("/{id}")
    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody())
    public ResponseEntity<CategoryRequest> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        Optional<CategoryDto> existingCategory = categoryService.getCategoryById(id);
        if (existingCategory.isPresent()) {
            CategoryDto categoryDto = mapToCategoryDto(categoryRequest);
            CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
            return ResponseEntity.ok(mapToCategoryRequest(updatedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Optional<CategoryDto> existingCategory = categoryService.getCategoryById(id);
        if (existingCategory.isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private CategoryDto mapToCategoryDto(CategoryRequest categoryRequest) {
        return CategoryDto.builder()
                .name(categoryRequest.getName())
                .build();
    }

    private CategoryRequest mapToCategoryRequest(CategoryDto categoryDto) {
        return CategoryRequest.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

}
