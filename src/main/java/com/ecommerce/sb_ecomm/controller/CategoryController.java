package com.ecommerce.sb_ecomm.controller;

import com.ecommerce.sb_ecomm.model.Category;
import com.ecommerce.sb_ecomm.payload.CategoryDTO;
import com.ecommerce.sb_ecomm.payload.CategoryResponse;
import com.ecommerce.sb_ecomm.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getCategories() {
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/categories/{category_id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable Long category_id) {
        String status = categoryService.deleteCategory(category_id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @RequestMapping(value = "/public/categories/{category_id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long category_id) {
        Category savedCategory = categoryService.updateCategory(category, category_id);
        return new ResponseEntity<>("Category with category id: " + category_id + " Has been updated ", HttpStatus.OK);
    }
}
