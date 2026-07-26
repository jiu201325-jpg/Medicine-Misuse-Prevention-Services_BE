package com.example.medicines.loader;

import com.example.medicines.entity.Medicine;
import com.example.medicines.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MedicineDataLoader implements CommandLineRunner {

    private final MedicineRepository medicineRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (medicineRepository.count() > 0) {
            System.out.println("이미 데이터가 있어 적재를 건너뜁니다. (" + medicineRepository.count() + "건)");
            return;
        }

        try (InputStream is = new ClassPathResource("medicines.json").getInputStream()) {
            List<MedicineSeedDto> seeds = objectMapper.readValue(
                    is,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, MedicineSeedDto.class)
            );

            List<Medicine> medicines = seeds.stream()
                    .map(s -> Medicine.builder()
                            .category(s.category())
                            .ingredient(s.ingredient())
                            .name(s.name())
                            .productNames(s.product_names())
                            .effect(s.effect())
                            .usage(s.usage())
                            .precautions(s.precautions())
                            .emergencySigns(s.emergency_signs())
                            .interactions(s.interactions())
                            .easyExplanation(s.easy_explanation())
                            .audioUrl(s.audio_url())
                            .build())
                    .toList();

            medicineRepository.saveAll(medicines);
            System.out.println(medicines.size() + "건의 약 데이터를 적재했습니다.");
        }
    }
}