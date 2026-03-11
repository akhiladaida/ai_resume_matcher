package com.akhila.ai_resume_matcher.service;

import com.akhila.ai_resume_matcher.entity.Candidate;
import com.akhila.ai_resume_matcher.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate createCandidate(Candidate candidate){
        return candidateRepository.save(candidate);
    }
    public List<Candidate> getAll(){
        return candidateRepository.findAll();
    }
    public Candidate getById(Long id){
        return candidateRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Candidate not found"));
    }
}
