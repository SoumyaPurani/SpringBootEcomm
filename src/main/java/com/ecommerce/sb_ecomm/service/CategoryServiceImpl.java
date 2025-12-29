package com.ecommerce.sb_ecomm.service;

import com.ecommerce.sb_ecomm.exceptions.APIException;
import com.ecommerce.sb_ecomm.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecomm.model.Category;
import com.ecommerce.sb_ecomm.payload.CategoryDTO;
import com.ecommerce.sb_ecomm.payload.CategoryResponse;
import com.ecommerce.sb_ecomm.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) throws APIException {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if(categoryRepository.findAll().isEmpty()) {
            throw new APIException("No Category found");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategories(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalRecords(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category "+ category.getCategoryName() +" already exists!!!");
        }
        Category createdCategory = categoryRepository.save(category);
        return modelMapper.map(createdCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", category_id));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long category_id) {
        Category savedCategory = categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", category_id));
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(category_id);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


}
