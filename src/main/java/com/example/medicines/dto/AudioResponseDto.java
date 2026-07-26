package com.example.medicines.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AudioResponseDto {
    private final Long medicineId;
    private final String audioUrl;
    private final int durationSec;
}
