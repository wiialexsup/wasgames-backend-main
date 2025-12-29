package com.wasgames.wasgames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wasgames.wasgames.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail (String email);
    Optional<User> findByUsername (String username);
}