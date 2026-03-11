package com.akhila.ai_resume_matcher.service;

import com.akhila.ai_resume_matcher.entity.MatchResult;
import com.akhila.ai_resume_matcher.repository.MatchResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchResultService {

    private final MatchResultRepository matchResultRepository;
    public MatchResultService(MatchResultRepository matchResultRepository) {
        this.matchResultRepository = matchResultRepository;
    }
    public List<MatchResult> findAll(Long jdId) {
        return matchResultRepository.findByJdIdOrderByFinalScoreDesc(jdId);
    }
}
