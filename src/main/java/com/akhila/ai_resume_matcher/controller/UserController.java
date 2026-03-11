package com.akhila.ai_resume_matcher.controller;

import com.akhila.ai_resume_matcher.entity.User;
import com.akhila.ai_resume_matcher.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping
    public User CreateUser(){
        return userService.createUser(
                "test@example.com",
                "hashed-password",
                "recruiter"
        );
    }
    @GetMapping
    public List<User> getUser(){
        return userService.getAllUser();
    }
}
