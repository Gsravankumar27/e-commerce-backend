package com.sravan.e_commerce_backend.controller;

import com.sravan.e_commerce_backend.model.Category;
import com.sravan.e_commerce_backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // POST API: కేటగిరీ క్రియేట్ చేయడానికి (URL: http://localhost:8081/api/categories)
    @PostMapping
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // GET API: అన్ని కేటగిరీలను చూడటానికి (URL: http://localhost:8081/api/categories)
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}