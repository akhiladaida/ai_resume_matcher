package com.akhila.ai_resume_matcher.service;

import com.akhila.ai_resume_matcher.entity.JobDescription;
import com.akhila.ai_resume_matcher.repository.JobDescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDescriptionService {

    private final JobDescriptionRepository repo;

    public JobDescriptionService(JobDescriptionRepository repo) {
        this.repo = repo;
    }

    public JobDescription create(JobDescription jd) {
        return repo.save(jd);
    }

    public JobDescription getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("JD not found"));
    }

    public List<JobDescription> getAll() {
        return repo.findAll();
    }
}
