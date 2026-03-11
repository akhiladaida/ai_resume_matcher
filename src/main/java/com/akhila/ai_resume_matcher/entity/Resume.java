package com.akhila.ai_resume_matcher.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name="file_name")
    private String fileName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name="uploaded_at")
    private Instant uploadedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name="candidate_id")
    private Candidate candidate;

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
