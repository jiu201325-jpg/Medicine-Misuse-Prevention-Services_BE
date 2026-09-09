package com.example.medicines.service;

import com.example.medicines.dto.*;
import com.example.medicines.entity.Medicine;
import com.example.medicines.exception.CustomException;
import com.example.medicines.exception.ErrorCode;
import com.example.medicines.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final ClovaStudioClient clovaStudioClient;
    private final Random random = new Random();

    public MedicineListDto getAll() {
        List<Medicine> all = medicineRepository.findAll();
        return toListDto(all);
    }

    public MedicineListDto search(String query) {
        if (query == null || query.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_QUERY);
        }
        return toListDto(medicineRepository.searchByNameOrIngredient(query));
    }

    public MedicineDetailDto getDetail(Long id) {
        Medicine m = medicineRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEDICINE_NOT_FOUND));
        return new MedicineDetailDto(m);
    }

    public AudioResponseDto getAudio(Long id) {
        Medicine m = medicineRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEDICINE_NOT_FOUND));
        // TODO: 실제 CLOVA Voice 연동 시 파일 길이 계산
        return new AudioResponseDto(m.getId(), m.getAudioUrl(), 12);
    }

    public ScanResultDto scan(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new CustomException(ErrorCode.MISSING_IMAGE);
        }

        String filename = image.getOriginalFilename() == null ? "" : image.getOriginalFilename();
        String recognizedText = filename.replaceAll("\\.[a-zA-Z]+$", "").trim();

        if (recognizedText.isBlank()) {
            return ScanResultDto.notFound();
        }

        List<Medicine> matches = medicineRepository.searchByNameOrIngredient(recognizedText);

        if (matches.isEmpty()) {
            return ScanResultDto.notFound();
        } else if (matches.size() == 1) {
            return ScanResultDto.matched(recognizedText, new MedicineDetailDto(matches.get(0)));
        } else {
            List<MedicineSummaryDto> candidates = matches.stream()
                    .map(MedicineSummaryDto::new)
                    .collect(Collectors.toList());
            return ScanResultDto.multipleCandidates(recognizedText, candidates);
        }
    }

    @Transactional
    public Map<String, Integer> generateExplanations() {
        List<Medicine> all = medicineRepository.findAll();
        int updated = 0, failed = 0;

        for (Medicine m : all) {
            try {
                String explanation = clovaStudioClient.generateEasyExplanation(
                        m.getEffect(),
                        m.getUsage(),
                        m.getPrecautions(),
                        String.join(", ", m.getEmergencySigns())
                );
                m.updateEasyExplanation(explanation);
                updated++;
            } catch (Exception e) {
                failed++;
            }
        }
        return Map.of("updated", updated, "failed", failed);
    }

    private MedicineListDto toListDto(List<Medicine> medicines) {
        List<MedicineSummaryDto> results = medicines.stream()
                .map(MedicineSummaryDto::new)
                .collect(Collectors.toList());
        return new MedicineListDto(results.size(), results);
    }
}