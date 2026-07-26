package com.example.medicines.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class ScanResultDto {
    private final String recognizedText;
    private final String matchStatus;       // matched / multiple_candidates / not_found
    private final MedicineDetailDto medicine;      // matched일 때만
    private final List<MedicineSummaryDto> candidates; // multiple_candidates일 때만

    private ScanResultDto(String recognizedText, String matchStatus,
                          MedicineDetailDto medicine, List<MedicineSummaryDto> candidates) {
        this.recognizedText = recognizedText;
        this.matchStatus = matchStatus;
        this.medicine = medicine;
        this.candidates = candidates;
    }

    public static ScanResultDto matched(String recognizedText, MedicineDetailDto medicine) {
        return new ScanResultDto(recognizedText, "matched", medicine, null);
    }

    public static ScanResultDto multipleCandidates(String recognizedText, List<MedicineSummaryDto> candidates) {
        return new ScanResultDto(recognizedText, "multiple_candidates", null, candidates);
    }

    public static ScanResultDto notFound() {
        return new ScanResultDto("", "not_found", null, List.of());
    }
}
