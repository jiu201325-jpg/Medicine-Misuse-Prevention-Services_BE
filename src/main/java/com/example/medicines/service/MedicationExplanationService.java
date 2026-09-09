package com.example.medicines.service;

import com.example.medicines.dto.MedicationExplanationResponse;
import com.example.medicines.entity.InteractionRule;
import com.example.medicines.entity.Medicine;
import com.example.medicines.repository.InteractionRuleRepository;
import com.example.medicines.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicationExplanationService {

    private static final String MEDICAL_DISCLAIMER =
            "\n\n※ 본 설명은 참고용이며, 정확한 복용법은 반드시 의사·약사와 상담하세요.";

    private final MedicineRepository medicineRepository;
    private final InteractionRuleRepository interactionRuleRepository;

    public MedicationExplanationService(MedicineRepository medicineRepository,
                                        InteractionRuleRepository interactionRuleRepository) {
        this.medicineRepository = medicineRepository;
        this.interactionRuleRepository = interactionRuleRepository;
    }

    public MedicationExplanationResponse getMedicationExplanation(List<Long> medicineIds) {

        List<Medicine> medicines = medicineRepository.findAllById(medicineIds);

        // 1. 규칙 기반 상호작용 경고 (사용자가 등록한 약물 전체 쌍 검사)
        List<String> ruleBasedWarnings = new ArrayList<>();
        for (int i = 0; i < medicines.size(); i++) {
            for (int j = i + 1; j < medicines.size(); j++) {
                List<InteractionRule> matches = interactionRuleRepository.findByDrugPair(
                        medicines.get(i).getId(), medicines.get(j).getId());
                for (InteractionRule rule : matches) {
                    ruleBasedWarnings.add(rule.getWarningMessage());
                }
            }
        }

        // 2. 캐싱된 LLM 설명 사용 (약물 단위 캐시, 병용 정보 없음)
        //    주 대상이 1개 약물이라고 가정. 여러 개면 앞쪽 것만 대표로 사용하거나
        //    화면에서 약물별로 반복 호출하는 구조인지 확인 필요.
        Medicine primary = medicines.get(0);
        String llmExplanation = primary.getEasyExplanation(); // 배치에서 미리 생성해둔 값

        // 3. 고정 문구 append
        String finalExplanation = llmExplanation + MEDICAL_DISCLAIMER;

        // 4. 응답 조립
        return MedicationExplanationResponse.builder()
                .llmExplanation(finalExplanation)
                .ruleBasedWarnings(ruleBasedWarnings)
                .build();
    }
}