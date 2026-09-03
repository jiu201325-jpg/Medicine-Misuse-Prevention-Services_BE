package com.example.medicines.controller;

import com.example.medicines.service.ClovaVoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VoiceController {

    private final ClovaVoiceService clovaVoiceService;

    @PostMapping("/api/voice/generate")
    public ResponseEntity<Map<String, String>> generateVoice(
            @RequestBody Map<String, String> request) {
        String audioUrl = clovaVoiceService.generateAudioUrl(request.get("text"));
        return ResponseEntity.ok(Map.of("audioUrl", audioUrl));
    }
}