package com.akhila.ai_resume_matcher.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "embeddings")
public class Embedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_type", nullable = false)
    private String sourceType; // RESUME or JD

    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @Column(nullable = false)
    private String model;

    @Column(name = "vector_json", nullable = false, columnDefinition = "TEXT")
    private String vectorJson;

    // getters/setters
    public Long getId() { return id; }
    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }
    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getVectorJson() { return vectorJson; }
    public void setVectorJson(String vectorJson) { this.vectorJson = vectorJson; }
}
