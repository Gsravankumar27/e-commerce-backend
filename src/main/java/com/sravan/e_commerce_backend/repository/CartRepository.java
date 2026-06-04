package com.sravan.e_commerce_backend.repository;

import com.sravan.e_commerce_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // యూజర్ ఐడీ ద్వారా కార్ట్ ని వెతకడానికి (Derived Query మ్యాజిక్)
    Optional<Cart> findByUserId(Long userId);
}