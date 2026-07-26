package com.example.medicines.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class MedicineListDto {
    private final int count;
    private final List<MedicineSummaryDto> results;
}