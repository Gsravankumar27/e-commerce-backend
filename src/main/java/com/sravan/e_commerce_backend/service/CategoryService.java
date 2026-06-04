package com.sravan.e_commerce_backend.service;

import com.sravan.e_commerce_backend.model.Category;
import com.sravan.e_commerce_backend.repository.CategoryRepository;
import com.sravan.e_commerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. కొత్త కేటగిరీని సేవ్ చేయడానికి
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // 2. అన్ని కేటగిరీలను లిస్ట్ చేయడానికి
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 3. ID ద్వారా కేటగిరీని వెతకడానికి
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}