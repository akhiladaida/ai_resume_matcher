package com.akhila.ai_resume_matcher.repository;

import com.akhila.ai_resume_matcher.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
