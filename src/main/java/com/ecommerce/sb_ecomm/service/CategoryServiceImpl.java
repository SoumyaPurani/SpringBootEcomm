package com.ecommerce.sb_ecomm.service;

import com.ecommerce.sb_ecomm.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        if (categories.isEmpty()) {
            category.setCategory_id(1L);
            categories.add(category);
        } else {
            category.setCategory_id(categories.size() + 1L);
            categories.add(category);
        }
    }

    @Override
    public String deleteCategory(Long category_id) {
        Category category = categories.stream().
                filter(e -> e.getCategory_id().equals(category_id)).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categories.remove(category);
        return category.getCategory_id() + " has been deleted";
    }


}
