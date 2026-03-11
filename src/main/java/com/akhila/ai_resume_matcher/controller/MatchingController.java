package com.akhila.ai_resume_matcher.controller;

import com.akhila.ai_resume_matcher.dto.MatchResponse;
import com.akhila.ai_resume_matcher.entity.MatchResult;
import com.akhila.ai_resume_matcher.repository.MatchResultRepository;
import com.akhila.ai_resume_matcher.service.MatchResultService;
import com.akhila.ai_resume_matcher.service.MatchingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchingController {

    private final MatchingService service;
    private final MatchResultService matchResultService;

    public MatchingController(MatchingService service, MatchResultService matchResultService) {

        this.service = service;
        this.matchResultService=matchResultService;
    }

    @GetMapping
    public MatchResponse match(@RequestParam Long resumeId, @RequestParam Long jdId) {
        return service.match(resumeId, jdId);
    }
    @GetMapping("/rank")
    public List<MatchResult> rank(@RequestParam Long jdId) {
       return matchResultService.findAll(jdId);
    }
}
