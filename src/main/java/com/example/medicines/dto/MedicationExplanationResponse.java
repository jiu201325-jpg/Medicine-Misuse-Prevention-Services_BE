package com.example.medicines.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MedicationExplanationResponse {
    private String llmExplanation;
    private List<String> ruleBasedWarnings;
    private String audioUrl;

}