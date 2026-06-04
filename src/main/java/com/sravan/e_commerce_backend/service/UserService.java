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

    // 1. SIGN UP (User Registration) లాజిక్
    public User registerUser(User user) {
        // యూజర్‌నేమ్ ఆల్రెడీ ఉందేమో చెక్ చేస్తున్నాం
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        // ఈమెయిల్ ఆల్రెడీ రిజిస్టర్ అయి ఉందేమో చెక్ చేస్తున్నాం
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        // నోట్: ఇక్కడ పాస్‌వర్డ్‌ను ఎన్‌కోడ్ చేయాలి (ఫ్యూచర్‌లో స్ప్రింగ్ సెక్యూరిటీ యాడ్ చేసినప్పుడు చేద్దాం)
        return userRepository.save(user);
    }

    // 2. SIGN IN (User Login) లాజిక్
    public User loginUser(String username, String password) {
        // యూజర్‌నేమ్ ఉందో లేదో వెతుకుతున్నాం
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // పాస్‌వర్డ్ మ్యాచ్ అయిందో లేదో చెక్ చేస్తున్నాం
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password!");
        }

        return user; // లాగిన్ సక్సెస్ అయితే యూజర్ ఆబ్జెక్ట్‌ను రిటర్న్ చేస్తాం
    }
}