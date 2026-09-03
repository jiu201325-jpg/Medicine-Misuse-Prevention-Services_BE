package com.example.medicines.controller;

import com.example.medicines.entity.Medicine;
import com.example.medicines.service.ClovaOcrService;
import com.example.medicines.service.MedicineMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OcrController {

    private final ClovaOcrService clovaOcrService;
    private final MedicineMatchService medicineMatchService;

    @PostMapping("/api/ocr/scan")
    public ResponseEntity<Map<String, Object>> scanMedication(
            @RequestParam("image") MultipartFile image) {
        String ocrText = clovaOcrService.extractMedicationName(image);
        Medicine matched = medicineMatchService.matchFromOcrText(ocrText);
        return ResponseEntity.ok(Map.of(
                "medicationId", matched.getId(),
                "medicationName", matched.getName()
        ));
    }
}