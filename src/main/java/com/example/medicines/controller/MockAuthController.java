package com.example.medicines.controller;

import com.example.medicines.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MockAuthController {

    private static final String FIXED_CODE = "123456"; // 고정 인증번호

    @PostMapping("/verify")
    public ApiResponse<?> verify(@RequestParam String phoneNumber, @RequestParam String inputCode) {
        boolean verified = FIXED_CODE.equals(inputCode);
        return ApiResponse.success(Map.of("verified", verified));
    }
}