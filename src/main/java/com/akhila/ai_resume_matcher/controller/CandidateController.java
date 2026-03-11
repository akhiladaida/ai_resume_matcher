package com.akhila.ai_resume_matcher.controller;

import com.akhila.ai_resume_matcher.dto.CandidateResponse;
import com.akhila.ai_resume_matcher.dto.CreateCandidateRequest;
import com.akhila.ai_resume_matcher.entity.Candidate;
import com.akhila.ai_resume_matcher.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public CandidateResponse createCandidate(@Valid @RequestBody CreateCandidateRequest request){
        Candidate candidate=new Candidate();
        candidate.setName(request.getName());
        candidate.setEmail(request.getEmail());
        candidate.setPhone(request.getPhone());
        candidate.setLocation(request.getLocation());

        Candidate saved=candidateService.createCandidate(candidate);
        return new CandidateResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getLocation()
        );
    }

    @GetMapping
    public List<Candidate> getAll(){
        return candidateService.getAll();
    }
}
