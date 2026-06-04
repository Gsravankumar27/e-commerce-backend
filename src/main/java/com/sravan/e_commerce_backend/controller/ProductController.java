package com.sravan.e_commerce_backend.controller;

import com.sravan.e_commerce_backend.model.Category;
import com.sravan.e_commerce_backend.model.Product;
import com.sravan.e_commerce_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private com.sravan.e_commerce_backend.service.CategoryService categoryService;

    // 1. POST API: ప్రొడక్ట్‌ను క్రియేట్ చేయడానికి (URL: http://localhost:8081/api/products)
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        // ఒకవేళ ప్రొడక్ట్ JSON లో కేటగిరీ ఐడీ పంపితే, ఆ కేటగిరీని వెతికి ప్రొడక్ట్‌కి లింక్ చేస్తాం
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);
        }
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // 2. GET API: అన్ని ప్రొడక్ట్స్‌ను చూడటానికి (URL: http://localhost:8081/api/products)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    // 3. GET API: ID ద్వారా ప్రొడక్ట్ చూడటానికి (URL: http://localhost:8081/api/products/1)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // 4. PUT API: ప్రొడక్ట్ అప్‌డేట్ చేయడానికి (URL: http://localhost:8081/api/products/1)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@Valid @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // 5. DELETE API: ప్రొడక్ట్ డిలీట్ చేయడానికి (URL: http://localhost:8081/api/products/1)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
    }
}