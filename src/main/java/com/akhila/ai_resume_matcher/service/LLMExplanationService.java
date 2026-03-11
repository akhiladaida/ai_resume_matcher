package com.akhila.ai_resume_matcher.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LLMExplanationService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.openai.api-key}")
    private String apiKey;

    @Value("${ai.openai.chat-model:gpt-4o-mini}")
    private String chatModel;

    public String generateExplanation(String jdTitle,
                                      String jdDescription,
                                      String resumeText,
                                      int finalScore,
                                      List<String> matchedSkills,
                                      List<String> missingSkills) {

        String trimmedResume = resumeText == null ? "" :
                resumeText.substring(0, Math.min(resumeText.length(), 3000));

        String prompt = """
                You are helping a recruiter understand why a candidate matched a job description.

                Write a short, professional explanation in 3-5 sentences.

                Rules:
                - Be factual and grounded only in the provided data.
                - Mention strengths based on matched skills.
                - Mention gaps based on missing skills.
                - Do not invent experience not present in the input.
                - Do not say "I think" or "maybe".
                - Keep it concise and recruiter-friendly.

                Job Title:
                %s

                Job Description:
                %s

                Candidate Resume Text:
                %s

                Final Score:
                %d

                Matched Skills:
                %s

                Missing Skills:
                %s
                """.formatted(
                jdTitle,
                jdDescription,
                trimmedResume,
                finalScore,
                matchedSkills,
                missingSkills
        );

        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> systemMessage = Map.of(
                "role", "system",
                "content", "You are a precise recruiting assistant."
        );

        Map<String, Object> userMessage = Map.of(
                "role", "user",
                "content", prompt
        );

        Map<String, Object> requestBody = Map.of(
                "model", chatModel,
                "messages", List.of(systemMessage, userMessage),
                "temperature", 0.2
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
        );

        List choices = (List) response.getBody().get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");

        return message.get("content").toString().trim();
    }
}