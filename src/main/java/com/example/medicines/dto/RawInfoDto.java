package com.example.medicines.dto;

import com.example.medicines.entity.Medicine;
import lombok.Getter;
import java.util.List;

@Getter
public class RawInfoDto {
    private final String usage;
    private final String effect;
    private final String precautions;
    private final List<String> emergencySigns;
    private final List<String> interactions;

    public RawInfoDto(Medicine m) {
        this.usage = m.getUsage();
        this.effect = m.getEffect();
        this.precautions = m.getPrecautions();
        this.emergencySigns = m.getEmergencySigns();
        this.interactions = m.getInteractions();
    }
}
