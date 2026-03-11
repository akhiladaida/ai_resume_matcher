package com.akhila.ai_resume_matcher.repository;

import com.akhila.ai_resume_matcher.entity.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmbeddingRepository extends JpaRepository<Embedding,Long> {
    Optional<Embedding> findTopBySourceTypeAndSourceIdOrderByIdDesc(String sourceType, Long sourceId);
}
