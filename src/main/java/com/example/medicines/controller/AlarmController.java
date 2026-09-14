package com.example.medicines.controller;

import com.example.medicines.dto.AlarmRequestDto;
import com.example.medicines.dto.ApiResponse;
import com.example.medicines.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping
    public ApiResponse<?> create(@RequestParam Long userId, @RequestBody AlarmRequestDto dto) {
        return ApiResponse.success(alarmService.create(userId, dto));
    }

    @GetMapping
    public ApiResponse<?> getByUser(@RequestParam Long userId) {
        return ApiResponse.success(alarmService.getByUser(userId));
    }
}
