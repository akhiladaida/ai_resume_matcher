package com.akhila.ai_resume_matcher.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_descriptions")
public class JobDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "must_have_skills", nullable = false)
    private String mustHaveSkills; // comma separated

    @Column(name = "nice_to_have_skills")
    private String niceToHaveSkills; // comma separated

    // getters & setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMustHaveSkills() { return mustHaveSkills; }
    public void setMustHaveSkills(String mustHaveSkills) { this.mustHaveSkills = mustHaveSkills; }

    public String getNiceToHaveSkills() { return niceToHaveSkills; }
    public void setNiceToHaveSkills(String niceToHaveSkills) { this.niceToHaveSkills = niceToHaveSkills; }
}
