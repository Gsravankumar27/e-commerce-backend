package com.sravan.e_commerce_backend.service;

import com.sravan.e_commerce_backend.model.Cart;
import com.sravan.e_commerce_backend.model.CartItem;
import com.sravan.e_commerce_backend.model.Product;
import com.sravan.e_commerce_backend.model.User;
import com.sravan.e_commerce_backend.repository.CartItemRepository;
import com.sravan.e_commerce_backend.repository.CartRepository;
import com.sravan.e_commerce_backend.repository.ProductRepository;
import com.sravan.e_commerce_backend.repository.UserRepository;
import com.sravan.e_commerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. యూజర్ ఐడీ ద్వారా కార్ట్ వివరాలు చూడటానికి
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
    }

    // 2. కార్ట్ లోకి ప్రొడక్ట్ యాడ్ చేసే మెయిన్ బిజినెస్ లాజిక్
    @Transactional
    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {
        // ఎ) యూజర్ ఉన్నాడో లేదో వెతకాలి
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // బి) ప్రొడక్ట్ ఉందో లేదో వెతకాలి
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // సి) యూజర్‌కి ఆల్రెడీ కార్ట్ ఉందో లేదో చూస్తాం, లేకపోతే కొత్త కార్ట్ క్రియేట్ చేస్తాం
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        // డి) కార్ట్ లో ఆల్రెడీ ఈ ప్రొడక్ట్ ఉందో లేదో చెక్ చేస్తాం
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // ఆల్రెడీ ఉంటే క్వాంటిటీ పెంచుతాం
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // లేకపోతే కొత్త కార్ట్ ఐటమ్ క్రియేట్ చేసి లింక్ చేస్తాం
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        // కార్ట్ ని సేవ్ చేస్తే క్యాస్కేడింగ్ (CascadeType.ALL) వల్ల ఐటమ్స్ కూడా ఆటోమేటిక్ గా సేవ్ అవుతాయి
        return cartRepository.save(cart);
    }
    // 3. కార్ట్ లో ఉన్న ప్రొడక్ట్ క్వాంటిటీని మార్చడానికి (e.g., UI లో + లేదా - కొట్టినప్పుడు)
    @Transactional
    public Cart updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

        if (quantity <= 0) {
            // క్వాంటిటీ 0 లేదా అంతకంటే తగ్గితే ఆ ఐటమ్‌ను కార్ట్ నుండి తీసేస్తాం
            cart.getItems().remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    // 4. ఒక నిర్దిష్ట ప్రొడక్ట్‌ను కార్ట్ నుండి పూర్తిగా డిలీట్ చేయడానికి
    @Transactional
    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

        cart.getItems().remove(cartItem);
        return cartRepository.save(cart);
    }
}