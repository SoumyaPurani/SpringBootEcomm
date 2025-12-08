package com.ecommerce.sb_ecomm.controller;

import com.ecommerce.sb_ecomm.model.Category;
import com.ecommerce.sb_ecomm.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added Successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/categories/{category_id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable Long category_id) {
        try {
            String status = categoryService.deleteCategory(category_id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @RequestMapping(value = "/public/categories/{category_id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long category_id) {
        try{
            Category savedCategory = categoryService.updateCategory(category, category_id);
            return new ResponseEntity<>("Category with category id: " + category_id + " Has been updated ", HttpStatus.OK);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
