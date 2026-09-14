package com.example.medicines.controller;

import com.example.medicines.dto.ApiResponse;
import com.example.medicines.service.GuardianLinkService;
import com.example.medicines.service.LinkCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/guardian")
@RequiredArgsConstructor
public class GuardianController {

    private final LinkCodeService linkCodeService;
    private final GuardianLinkService guardianLinkService;

    @PostMapping("/link-code")
    public ApiResponse<?> generateLinkCode(@RequestParam Long elderId) {
        return ApiResponse.success(Map.of("code", linkCodeService.generateCode(elderId)));
    }

    @PostMapping("/link")
    public ApiResponse<?> link(@RequestParam String code, @RequestParam Long guardianId) {
        guardianLinkService.linkGuardian(code, guardianId);
        return ApiResponse.success(Map.of("linked", true));
    }
}
