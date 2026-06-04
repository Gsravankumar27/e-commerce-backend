package com.sravan.e_commerce_backend.controller;

import com.sravan.e_commerce_backend.model.Cart;
import com.sravan.e_commerce_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // 1. POST API: కార్ట్ లోకి ఐటమ్ యాడ్ చేయడానికి (URL: http://localhost:8081/api/carts/1/add?productId=1&quantity=2)
    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        Cart updatedCart = cartService.addItemToCart(userId, productId, quantity);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    // 2. GET API: యూజర్ ఐడీ బట్టి కార్ట్ చూడటానికి (URL: http://localhost:8081/api/carts/1)
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    // 3. PUT API: క్వాంటిటీ అప్‌డేట్ చేయడానికి (URL: http://localhost:8081/api/carts/1/update?productId=1&quantity=5)
    @PutMapping("/{userId}/update")
    public ResponseEntity<Cart> updateCartItemQuantity(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        Cart updatedCart = cartService.updateCartItemQuantity(userId, productId, quantity);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    // 4. DELETE API: కార్ట్ నుండి ప్రొడక్ట్ తీసేయడానికి (URL: http://localhost:8081/api/carts/1/remove?productId=1)
    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long userId,
            @RequestParam Long productId) {

        Cart updatedCart = cartService.removeItemFromCart(userId, productId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }
}