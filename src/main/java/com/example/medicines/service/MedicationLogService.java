package com.example.medicines.service;

import com.example.medicines.dto.MedicationLogRequestDto;
import com.example.medicines.entity.MedicationLog;
import com.example.medicines.repository.MedicationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationLogService {

    private final MedicationLogRepository medicationLogRepository;

    public MedicationLog create(Long userId, MedicationLogRequestDto dto) {
        MedicationLog log = MedicationLog.builder()
                .userId(userId)
                .medicineId(dto.getMedicineId())
                .checkedAt(dto.getCheckedAt())
                .taken(dto.isTaken())
                .build();
        return medicationLogRepository.save(log);
    }

    public List<MedicationLog> getByUser(Long userId) {
        return medicationLogRepository.findByUserId(userId);
    }

    // 비로그인 로컬 기록 → 로그인 전환 시 일괄 저장
    @Transactional
    public int migrate(Long userId, List<MedicationLogRequestDto> localLogs) {
        List<MedicationLog> logs = localLogs.stream()
                .map(dto -> MedicationLog.builder()
                        .userId(userId)
                        .medicineId(dto.getMedicineId())
                        .checkedAt(dto.getCheckedAt())
                        .taken(dto.isTaken())
                        .build())
                .collect(Collectors.toList());

        medicationLogRepository.saveAll(logs);
        return logs.size();
    }
}