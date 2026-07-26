package com.example.medicines.dto;

import com.example.medicines.entity.Medicine;
import lombok.Getter;

@Getter
public class MedicineDetailDto {
    private final Long id;
    private final String name;
    private final String category;
    private final RawInfoDto rawInfo;
    private final String easyExplanation;
    private final boolean hasAudio;

    public MedicineDetailDto(Medicine m) {
        this.id = m.getId();
        this.name = m.getName();
        this.category = m.getCategory();
        this.rawInfo = new RawInfoDto(m);
        this.easyExplanation = m.getEasyExplanation();
        this.hasAudio = m.getAudioUrl() != null;
    }
}
