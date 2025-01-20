package com.example.board.repository;

import com.example.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByIdAndStatus(String id, String status);
    Optional<User> findById(String id);
    boolean existsById(String id);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
