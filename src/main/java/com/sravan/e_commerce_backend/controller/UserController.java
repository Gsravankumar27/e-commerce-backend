package com.sravan.e_commerce_backend.controller;

import com.sravan.e_commerce_backend.model.User;
import com.sravan.e_commerce_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. POST API: User Registration (URL: http://localhost:8081/api/users/signup)
    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // 2. POST API: User Login (URL: http://localhost:8081/api/users/login)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            User user = userService.loginUser(username, password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            // లాగిన్ ఫెయిల్ అయితే క్లీన్ గా బాడ్ రిక్వెస్ట్ ఎర్రర్ పంపుతాం
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}