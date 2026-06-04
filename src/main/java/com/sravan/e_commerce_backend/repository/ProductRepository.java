package com.sravan.e_commerce_backend.repository;

import com.sravan.e_commerce_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // భవిష్యత్తులో ప్రొడక్ట్ నేమ్ తో సెర్చ్ చేయడానికి కస్టమ్ క్వెరీస్ ఇక్కడ రాసుకోవచ్చు
}