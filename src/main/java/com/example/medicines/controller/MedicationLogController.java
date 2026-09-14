package com.example.medicines.controller;

import com.example.medicines.dto.ApiResponse;
import com.example.medicines.dto.MedicationLogRequestDto;
import com.example.medicines.service.MedicationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medication-logs")
@RequiredArgsConstructor
public class MedicationLogController {

    private final MedicationLogService medicationLogService;

    @PostMapping
    public ApiResponse<?> create(@RequestParam Long userId, @RequestBody MedicationLogRequestDto dto) {
        return ApiResponse.success(medicationLogService.create(userId, dto));
    }

    @GetMapping
    public ApiResponse<?> getByUser(@RequestParam Long userId) {
        return ApiResponse.success(medicationLogService.getByUser(userId));
    }

    @PostMapping("/migrate")
    public ApiResponse<?> migrate(@RequestParam Long userId, @RequestBody List<MedicationLogRequestDto> localLogs) {
        int count = medicationLogService.migrate(userId, localLogs);
        return ApiResponse.success(Map.of("migrated", count));
    }
}
