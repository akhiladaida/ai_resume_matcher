package com.akhila.ai_resume_matcher.service;

import com.akhila.ai_resume_matcher.entity.Embedding;
import com.akhila.ai_resume_matcher.repository.EmbeddingRepository;
import com.akhila.ai_resume_matcher.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private final EmbeddingRepository embeddingRepo;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.openai.api-key}")
    private String apiKey;

    @Value("${ai.openai.embeddings-model}")
    private String model;

    public EmbeddingService(EmbeddingRepository embeddingRepo) {
        this.embeddingRepo = embeddingRepo;
    }

    public List<Double> getOrCreateEmbedding(String sourceType, Long sourceId, String text) {
        return embeddingRepo.findTopBySourceTypeAndSourceIdOrderByIdDesc(sourceType, sourceId)
                .map(e -> JsonUtil.fromJsonArray(e.getVectorJson()))
                .orElseGet(() -> {
                    List<Double> vector = callOpenAIEmbeddings(text);

                    Embedding embedding = new Embedding();
                    embedding.setSourceType(sourceType);
                    embedding.setSourceId(sourceId);
                    embedding.setModel(model);
                    embedding.setVectorJson(JsonUtil.toJsonArray(vector));
                    embeddingRepo.save(embedding);

                    return vector;
                });
    }

    private List<Double> callOpenAIEmbeddings(String inputText) {
        String url = "https://api.openai.com/v1/embeddings";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", model,
                "input", inputText.length() > 8000 ? inputText.substring(0, 8000) : inputText
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        Map data0 = (Map) ((List) response.getBody().get("data")).get(0);
        return (List<Double>) data0.get("embedding");
    }
}
