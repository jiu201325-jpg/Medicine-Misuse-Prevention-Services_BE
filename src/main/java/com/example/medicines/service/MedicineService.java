package com.example.medicines.service;

import com.example.medicines.dto.*;
import com.example.medicines.entity.Medicine;
import com.example.medicines.exception.CustomException;
import com.example.medicines.exception.ErrorCode;
import com.example.medicines.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
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

        // TODO: 여기서 CLOVA OCR 호출 → recognizedText 추출
        // mock 단계: 파일명에 상품명이 포함되어 있으면 그걸로 매칭, 없으면 랜덤 분기
        String filename = image.getOriginalFilename() == null ? "" : image.getOriginalFilename();

        List<Medicine> all = medicineRepository.findAll();
        Medicine matched = all.stream()
                .filter(m -> m.getProductNames().stream().anyMatch(filename::contains))
                .findFirst()
                .orElse(null);

        if (matched != null) {
            return ScanResultDto.matched(matched.getName(), new MedicineDetailDto(matched));
        }

        double roll = random.nextDouble();
        if (roll < 0.7) {
            Medicine picked = all.get(random.nextInt(all.size()));
            return ScanResultDto.matched(picked.getName(), new MedicineDetailDto(picked));
        } else if (roll < 0.9) {
            List<MedicineSummaryDto> candidates = all.stream()
                    .limit(2)
                    .map(MedicineSummaryDto::new)
                    .collect(Collectors.toList());
            return ScanResultDto.multipleCandidates("인식 불확실", candidates);
        } else {
            return ScanResultDto.notFound();
        }
    }

    private MedicineListDto toListDto(List<Medicine> medicines) {
        List<MedicineSummaryDto> results = medicines.stream()
                .map(MedicineSummaryDto::new)
                .collect(Collectors.toList());
        return new MedicineListDto(results.size(), results);
    }
}