package com.akhila.ai_resume_matcher.service;

import com.akhila.ai_resume_matcher.entity.Candidate;
import com.akhila.ai_resume_matcher.entity.Resume;
import com.akhila.ai_resume_matcher.repository.ResumeRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final CandidateService candidateService;
    private final Tika tika=new Tika();

    public ResumeService(ResumeRepository resumeRepository, CandidateService candidateService) {
        this.resumeRepository = resumeRepository;
        this.candidateService = candidateService;
    }

    public Resume uploadResume(MultipartFile file, Long candidateId) throws Exception{
        if(file.isEmpty()){
            throw new RuntimeException("File if Empty");
        }

        if(!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".pdf")){
            throw new RuntimeException("Only PDF allowed");
        }

        String text=tika.parseToString(file.getInputStream());
        Candidate candidate=candidateService.getById(candidateId);

        Resume resume=new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setContent(text);
        resume.setCandidate(candidate);

        return resumeRepository.save(resume);
    }
    public List<Resume> getAll(){
        return resumeRepository.findAll();
    }

}
