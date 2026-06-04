package com.sravan.e_commerce_backend.service;

import com.sravan.e_commerce_backend.exception.ResourceNotFoundException;
import com.sravan.e_commerce_backend.model.Product;
import com.sravan.e_commerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 1. కొత్త ప్రొడక్ట్‌ను సేవ్ చేయడానికి
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 2. అన్ని ప్రొడక్ట్స్‌ను లిస్ట్ చేయడానికి
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // 3. ఒక నిర్దిష్ట ప్రొడక్ట్‌ను దాని ID ద్వారా వెతకడానికి
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // 4. ఉన్న ప్రొడక్ట్ వివరాలను అప్‌డేట్ చేయడానికి
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStockQuantity(productDetails.getStockQuantity());
        existingProduct.setImageUrl(productDetails.getImageUrl());

        return productRepository.save(existingProduct);
    }

    // 5. ఒక ప్రొడక్ట్‌ను డిలీట్ చేయడానికి
    public void deleteProduct(Long id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }
}
