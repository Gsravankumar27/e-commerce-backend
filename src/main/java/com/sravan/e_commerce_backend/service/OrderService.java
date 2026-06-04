package com.sravan.e_commerce_backend.service;

import com.sravan.e_commerce_backend.model.*;
import com.sravan.e_commerce_backend.repository.*;
import com.sravan.e_commerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. PLACE ORDER (Checkout) లాజిక్
    @Transactional
    public Order placeOrder(Long userId) {
        // ఎ) యూజర్ కార్ట్ ఉందో లేదో వెతకాలి, అందులో ఐటమ్స్ ఉన్నాయో లేదో చూడాలి
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot place order. Cart is empty!");
        }

        // బి) కొత్త Order ఆబ్జెక్ట్ క్రియేట్ చేయడం
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus("SUCCESS");

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // సి) కార్ట్ లోని ప్రతీ ఐటమ్‌ను ఆర్డర్ ఐటమ్ కింద మార్చడం & స్టాక్ చెక్ చేయడం
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // స్టాక్ ఉందో లేదో చెక్ చేస్తున్నాం
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName()
                        + ". Available stock: " + product.getStockQuantity());
            }

            // స్టాక్ తగ్గించడం (Inventory Update)
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            // OrderItem బిల్డ్ చేయడం
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice()); // ప్రస్తుత ప్రైస్ సేవ్ చేస్తున్నాం
            orderItems.add(orderItem);

            // టోటల్ అమౌంట్ కౌంట్ చేయడం
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        // డి) ఆర్డర్ సేవ్ చేయడం
        Order savedOrder = orderRepository.save(order);

        // ఇ) ఆర్డర్ అయిపోయింది కాబట్టి కార్ట్ లోని ఐటమ్స్ అన్నీ క్లియర్ చేయడం (Empty Cart)
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    // 2. యూజర్ ఆర్డర్ హిస్టరీ చూడటానికి
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}