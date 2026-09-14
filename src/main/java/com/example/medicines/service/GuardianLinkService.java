package com.example.medicines.service;

import com.example.medicines.entity.GuardianRelation;
import com.example.medicines.entity.LinkCode;
import com.example.medicines.exception.CustomException;
import com.example.medicines.exception.ErrorCode;
import com.example.medicines.repository.GuardianRelationRepository;
import com.example.medicines.repository.LinkCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuardianLinkService {

    private final LinkCodeRepository linkCodeRepository;
    private final GuardianRelationRepository guardianRelationRepository;

    @Transactional
    public void linkGuardian(String code, Long guardianId) {
        LinkCode linkCode = linkCodeRepository.findByCode(code)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_LINK_CODE));

        if (!linkCode.isValid()) {
            throw new CustomException(ErrorCode.EXPIRED_LINK_CODE);
        }

        GuardianRelation relation = GuardianRelation.builder()
                .elderId(linkCode.getElderId())
                .guardianId(guardianId)
                .canViewMedicationLog(true)
                .canReceiveAlerts(true)
                .canEmergencyContact(false)
                .build();

        guardianRelationRepository.save(relation);
        linkCode.markUsed(); // dirty checking으로 저장
    }
    public void checkViewPermission(Long elderId, Long guardianId) {
        GuardianRelation relation = guardianRelationRepository
                .findByElderIdAndGuardianId(elderId, guardianId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_PERMISSION));

        if (!relation.isCanViewMedicationLog()) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }
}
