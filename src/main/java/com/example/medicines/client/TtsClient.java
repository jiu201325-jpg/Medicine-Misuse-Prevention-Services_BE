package com.example.medicines.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TtsClient {

    private final RestTemplate restTemplate;

    @Value("${python.tts.url:http://localhost:8000/tts}")
    private String ttsServiceUrl;

    public String requestTts(Long drugId, String explanationText) {
        Map<String, Object> request = new HashMap<>();
        request.put("drugId", drugId);
        request.put("text", explanationText);

        // Python 서비스가 {"audioUrl": "https://..."} 형태로 응답한다고 가정
        Map<String, String> response = restTemplate.postForObject(ttsServiceUrl, request, Map.class);

        if (response != null && response.containsKey("audioUrl")) {
            return response.get("audioUrl");
        }
        throw new RuntimeException("TTS 마이크로서비스 호출 실패 또는 audioUrl 누락");
    }
}
