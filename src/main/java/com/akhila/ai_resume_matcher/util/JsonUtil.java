package com.akhila.ai_resume_matcher.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJsonArray(List<Double> vector) {
        try {
            return MAPPER.writeValueAsString(vector);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize vector", e);
        }
    }

    public static List<Double> fromJsonArray(String json) {
        try {
            return MAPPER.readValue(json, new TypeReference<List<Double>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse vector", e);
        }
    }
}
