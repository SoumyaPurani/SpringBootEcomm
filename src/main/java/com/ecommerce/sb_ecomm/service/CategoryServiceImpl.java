package com.ecommerce.sb_ecomm.service;

import com.ecommerce.sb_ecomm.exceptions.APIException;
import com.ecommerce.sb_ecomm.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecomm.model.Category;
import com.ecommerce.sb_ecomm.payload.CategoryDTO;
import com.ecommerce.sb_ecomm.payload.CategoryResponse;
import com.ecommerce.sb_ecomm.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categoryRepository.findAll().isEmpty()) {
            throw new APIException("No Category found");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategories(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category "+ category.getCategoryName() +" already exists!!!");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", category_id));
        categoryRepository.delete(category);
        return category.getCategoryId() + " has been deleted";
    }

    @Override
    public Category updateCategory(Category category, Long category_id) {
        Category savedCategory = categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", category_id));
        category.setCategoryId(category_id);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }


}
