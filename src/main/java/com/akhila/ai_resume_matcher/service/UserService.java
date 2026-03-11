package com.akhila.ai_resume_matcher.service;

import com.akhila.ai_resume_matcher.entity.User;
import com.akhila.ai_resume_matcher.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(String email, String passwordHash, String role) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
