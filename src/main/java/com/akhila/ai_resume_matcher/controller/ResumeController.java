package com.akhila.ai_resume_matcher.controller;

import com.akhila.ai_resume_matcher.entity.Resume;
import com.akhila.ai_resume_matcher.service.ResumeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Resume upload(@RequestParam("file") MultipartFile file, @RequestParam("candidate_id") Long candidate_id) throws Exception {
        return resumeService.uploadResume(file,candidate_id);
    }
    @GetMapping
    public List<Resume> getAll() {
        return resumeService.getAll();
    }
}
