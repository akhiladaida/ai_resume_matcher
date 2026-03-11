package com.akhila.ai_resume_matcher.dto;

import java.util.List;

public class MatchResponse {
    private int score;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private String explanation;

    public MatchResponse(int score, List<String> matchedSkills, List<String> missingSkills, String explanation) {
        this.score = score;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.explanation = explanation;
    }

    public int getScore() { return score; }
    public List<String> getMatchedSkills() { return matchedSkills; }
    public List<String> getMissingSkills() { return missingSkills; }
    public String getExplanation() {
        return explanation;
    }
}
