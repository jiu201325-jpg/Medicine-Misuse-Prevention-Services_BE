package com.example.medicines.controller;

import com.example.medicines.dto.ApiResponse;
import com.example.medicines.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    // 2.1 POST /medicines/scan — 핵심 MVP
    @PostMapping("/scan")
    public ApiResponse<?> scan(@RequestParam("image") MultipartFile image) {
        return ApiResponse.success(medicineService.scan(image));
    }

    // 2.2 GET /medicines/{id}
    @GetMapping("/{id}")
    public ApiResponse<?> getDetail(@PathVariable Long id) {
        return ApiResponse.success(medicineService.getDetail(id));
    }

    // 2.3 GET /medicines/{id}/audio
    @GetMapping("/{id}/audio")
    public ApiResponse<?> getAudio(@PathVariable Long id) {
        return ApiResponse.success(medicineService.getAudio(id));
    }

    // 2.4 GET /medicines/search — 보조 기능
    @GetMapping("/search")
    public ApiResponse<?> search(@RequestParam String query) {
        return ApiResponse.success(medicineService.search(query));
    }

    // 2.5 GET /medicines — 관리용 전체 목록
    @GetMapping
    public ApiResponse<?> getAll() {
        return ApiResponse.success(medicineService.getAll());
    }
}
