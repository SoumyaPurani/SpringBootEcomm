package com.ecommerce.sb_ecomm.service;

import com.ecommerce.sb_ecomm.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecomm.model.Category;
import com.ecommerce.sb_ecomm.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    //private List<Category> categories = new ArrayList<>();
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", category_id));
        categoryRepository.delete(category);
        return category.getCategory_id() + " has been deleted";
    }

    @Override
    public Category updateCategory(Category category, Long category_id) {
        Category savedCategory = categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", category_id));
        category.setCategory_id(category_id);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }


}
