package com.akhila.ai_resume_matcher.controller;

import com.akhila.ai_resume_matcher.entity.JobDescription;
import com.akhila.ai_resume_matcher.service.JobDescriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jds")
public class JobDescriptionController {

    private final JobDescriptionService service;

    public JobDescriptionController(JobDescriptionService service) {
        this.service = service;
    }

    @PostMapping
    public JobDescription create(@RequestBody JobDescription jd) {
        return service.create(jd);
    }

    @GetMapping
    public List<JobDescription> getAll() {
        return service.getAll();
    }
}
