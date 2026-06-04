package com.sravan.e_commerce_backend.repository;

import com.sravan.e_commerce_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom method to find category by name if needed later
    Category findByName(String name);
}