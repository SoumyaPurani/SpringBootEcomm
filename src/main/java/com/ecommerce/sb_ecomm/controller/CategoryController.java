package com.ecommerce.sb_ecomm.controller;

import com.ecommerce.sb_ecomm.model.Category;
import com.ecommerce.sb_ecomm.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/api/public/categories")
    public String addCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return "Category added successfully";
    }

    @DeleteMapping("/api/admin/categories/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long category_id) {
        try {
            String status = categoryService.deleteCategory(category_id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
