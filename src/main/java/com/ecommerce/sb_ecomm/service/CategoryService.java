package com.ecommerce.sb_ecomm.service;


import com.ecommerce.sb_ecomm.payload.CategoryDTO;
import com.ecommerce.sb_ecomm.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO deleteCategory(Long category_id);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long category_id);
}
