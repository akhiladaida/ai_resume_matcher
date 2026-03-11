package com.akhila.ai_resume_matcher.repository;

import com.akhila.ai_resume_matcher.entity.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchResultRepository  extends JpaRepository<MatchResult,Long> {
    List<MatchResult> findByJdIdOrderByFinalScoreDesc(long jdId);
}
