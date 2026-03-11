package com.akhila.ai_resume_matcher.service;

import com.akhila.ai_resume_matcher.dto.MatchResponse;
import com.akhila.ai_resume_matcher.entity.JobDescription;
import com.akhila.ai_resume_matcher.entity.MatchResult;
import com.akhila.ai_resume_matcher.entity.Resume;
import com.akhila.ai_resume_matcher.repository.MatchResultRepository;
import com.akhila.ai_resume_matcher.repository.ResumeRepository;
import com.akhila.ai_resume_matcher.util.SimilarityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {

    private final ResumeRepository resumeRepo;
    private final JobDescriptionService jdService;
    private final EmbeddingService embeddingService;
    private final MatchResultRepository matchResultRepository;
    private final LLMExplanationService llmExplanationService;

    public MatchingService(ResumeRepository resumeRepo, JobDescriptionService jdService, EmbeddingService embeddingService, MatchResultRepository matchResultRepository,LLMExplanationService llmExplanationService) {
        this.resumeRepo = resumeRepo;
        this.jdService = jdService;
        this.embeddingService=embeddingService;
        this.matchResultRepository=matchResultRepository;
        this.llmExplanationService=llmExplanationService;
    }

    public MatchResponse match(Long resumeId, Long jdId) {

        Resume resume = resumeRepo.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        JobDescription jd = jdService.getById(jdId);

        String resumeText = resume.getContent().toLowerCase();

        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        String[] mustSkills = jd.getMustHaveSkills().split(",");

        for (String skill : mustSkills) {
            String s = skill.trim().toLowerCase();
            if (s.isEmpty()) continue;

            if (resumeText.contains(s)) matched.add(skill.trim());
            else missing.add(skill.trim());
        }

        int keywordScore = 0;
        if (mustSkills.length > 0) {
            keywordScore = (matched.size() * 100) / mustSkills.length;
        }
        List<Double> resumeVec=embeddingService.getOrCreateEmbedding("RESUME",resumeId,resumeText);
        List<Double> jdVec=embeddingService.getOrCreateEmbedding("JD",jdId, jd.getDescription());
        double semantic= SimilarityUtil.cosineSimilarity(resumeVec,jdVec);
        int semanticScore = (int)Math.round(semantic*100);
        int finalScore = (int) Math.round(
                (0.7 * keywordScore) +
                        (0.3 * semanticScore)
        );
        String explanation = llmExplanationService.generateExplanation(
                jd.getTitle(),
                jd.getDescription(),
                resume.getContent(),
                finalScore,
                matched,
                missing
        );
        MatchResult result = new MatchResult();
        result.setResumeId(resumeId);
        result.setJdId(jdId);
        result.setKeywordScore(keywordScore);
        result.setSemanticScore(semanticScore);
        result.setFinalScore(finalScore);
        result.setExplanation(explanation);

        matchResultRepository.save(result);
        return new MatchResponse(finalScore, matched, missing,explanation);
    }
}
