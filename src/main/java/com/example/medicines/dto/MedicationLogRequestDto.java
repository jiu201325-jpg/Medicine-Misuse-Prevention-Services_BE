package com.example.medicines.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationLogRequestDto {
    private Long medicineId;
    private LocalDateTime checkedAt;
    private boolean taken;
}
