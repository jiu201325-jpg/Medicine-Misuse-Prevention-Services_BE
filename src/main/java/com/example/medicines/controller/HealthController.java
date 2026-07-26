package com.example.medicines.controller;

import com.example.medicines.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {

    // 2.6 GET /health
    @GetMapping("/health")
    public ApiResponse<?> health() {
        return ApiResponse.success(Map.of("status", "ok"));
    }
}
