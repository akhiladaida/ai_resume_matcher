package com.akhila.ai_resume_matcher.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateCandidateRequest {

    @NotBlank(message = "Cannot be blank")
    private String name;

    @Email(message="Invalid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

