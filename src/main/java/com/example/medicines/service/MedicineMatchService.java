package com.example.medicines.service;

import com.example.medicines.entity.Medicine;
import com.example.medicines.exception.ExternalApiException;
import com.example.medicines.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineMatchService {

    private final MedicineRepository medicineRepository;

    public Medicine matchFromOcrText(String ocrText) {
        List<Medicine> allMedicines = medicineRepository.findAll();

        return allMedicines.stream()
                .filter(m -> ocrText.contains(m.getName()))
                .findFirst()
                .orElseThrow(() -> new ExternalApiException(
                        "MEDICATION_NOT_FOUND", "인식된 텍스트와 일치하는 약품을 찾을 수 없음", null));
    }
}