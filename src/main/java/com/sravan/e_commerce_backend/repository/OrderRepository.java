package com.sravan.e_commerce_backend.repository;

import com.sravan.e_commerce_backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // ఒక యూజర్ ఆర్డర్ హిస్టరీ మొత్తం చూడటానికి (Derived Query)
    List<Order> findByUserId(Long userId);
}