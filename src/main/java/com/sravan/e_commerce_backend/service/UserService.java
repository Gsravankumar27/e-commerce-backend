package com.sravan.e_commerce_backend.service;

import com.sravan.e_commerce_backend.model.User;
import com.sravan.e_commerce_backend.repository.UserRepository;
import com.sravan.e_commerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));


        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password!");
        }

        return user;
    }
}