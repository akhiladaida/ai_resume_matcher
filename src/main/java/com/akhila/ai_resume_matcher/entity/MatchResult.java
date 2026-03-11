package com.akhila.ai_resume_matcher.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "match_results")
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long resumeId;
    private Long jdId;

    private int keywordScore;
    private int semanticScore;
    private int finalScore;
    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    private Instant createdAt = Instant.now();

    // getters & setters

    public Long getId() {
        return id;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public Long getJdId() {
        return jdId;
    }

    public int getKeywordScore() {
        return keywordScore;
    }

    public int getSemanticScore() {
        return semanticScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public void setJdId(Long jdId) {
        this.jdId = jdId;
    }

    public void setKeywordScore(int keywordScore) {
        this.keywordScore = keywordScore;
    }

    public void setSemanticScore(int semanticScore) {
        this.semanticScore = semanticScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}