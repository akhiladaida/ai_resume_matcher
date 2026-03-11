package com.akhila.ai_resume_matcher.dto;

import lombok.Getter;

@Getter
public class CandidateResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String location;

    public CandidateResponse(Long id, String name, String email, String phone, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }

}
