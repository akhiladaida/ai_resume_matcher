package com.akhila.ai_resume_matcher.util;

import java.util.List;

public class SimilarityUtil {

    public static double cosineSimilarity(List<Double> a, List<Double> b) {
        if (a.size() != b.size()) throw new IllegalArgumentException("Vector size mismatch");

        double dot = 0.0, normA = 0.0, normB = 0.0;

        for (int i = 0; i < a.size(); i++) {
            double x = a.get(i);
            double y = b.get(i);
            dot += x * y;
            normA += x * x;
            normB += y * y;
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
