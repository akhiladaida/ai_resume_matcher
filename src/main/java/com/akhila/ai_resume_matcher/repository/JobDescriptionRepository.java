package com.akhila.ai_resume_matcher.repository;

import com.akhila.ai_resume_matcher.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDescriptionRepository extends JpaRepository<JobDescription,Long> {
}
