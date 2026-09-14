package com.example.medicines.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRequestDto {
    private Long medicineId;
    private LocalTime alarmTime;
    private boolean repeatDaily;
    private List<String> repeatDays;
}
