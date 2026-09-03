package com.example.medicines.controller;

import com.example.medicines.dto.ApiResponse;
import com.example.medicines.entity.Medicine;
import com.example.medicines.repository.MedicineRepository;
import com.example.medicines.service.ClovaStudioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MedicineRepository medicineRepository;
    private final ClovaStudioClient clovaStudioClient;

    @PostMapping("/generate-explanations")
    @Transactional
    public ApiResponse<?> generateExplanations() {
        List<Medicine> all = medicineRepository.findAll();
        int updated = 0, failed = 0;

        for (Medicine m : all) {
            try {
                String explanation = clovaStudioClient.generateEasyExplanation(
                        m.getEffect(), m.getUsage(), m.getPrecautions(),
                        String.join(", ", m.getEmergencySigns()));
                m.updateEasyExplanation(explanation);
                updated++;
            } catch (Exception e) {
                failed++;
            }
        }
        return ApiResponse.success(Map.of("updated", updated, "failed", failed));
    }
}
