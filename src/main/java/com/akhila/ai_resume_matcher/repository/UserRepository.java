package com.akhila.ai_resume_matcher.repository;

import com.akhila.ai_resume_matcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
