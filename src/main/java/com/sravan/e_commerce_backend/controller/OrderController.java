package com.sravan.e_commerce_backend.controller;

import com.sravan.e_commerce_backend.model.Order;
import com.sravan.e_commerce_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. POST API: Checkout / ఆర్డర్ ప్లేస్ చేయడానికి (URL: http://localhost:8081/api/orders/1/checkout)
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<Order> placeOrder(@PathVariable Long userId) {
        Order order = orderService.placeOrder(userId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // 2. GET API: ఒక యూజర్ ఆర్డర్ హిస్టరీ చూడటానికి (URL: http://localhost:8081/api/orders/1)
    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}