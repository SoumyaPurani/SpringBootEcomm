package com.ecommerce.sb_ecomm.service;


import com.ecommerce.sb_ecomm.model.Category;
import com.ecommerce.sb_ecomm.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long category_id);
    Category updateCategory(Category category, Long category_id);
}
