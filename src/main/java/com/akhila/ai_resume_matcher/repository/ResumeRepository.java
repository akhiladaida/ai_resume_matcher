package com.akhila.ai_resume_matcher.repository;

import com.akhila.ai_resume_matcher.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
