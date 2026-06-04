package com.sravan.e_commerce_backend.repository;

import com.sravan.e_commerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Username ద్వారా యూజర్‌ని వెతకడానికి (For Login)
    Optional<User> findByUsername(String username);

    // Email ఆల్రెడీ డేటాబేస్ లో ఉందో లేదో చెక్ చేయడానికి (For Signup validation)
    boolean existsByEmail(String email);

    // Username ఆల్రెడీ ఉందో లేదో చెక్ చేయడానికి
    boolean existsByUsername(String username);
}