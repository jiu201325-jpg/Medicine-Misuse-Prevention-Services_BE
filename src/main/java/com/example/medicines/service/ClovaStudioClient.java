package com.example.medicines.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ClovaStudioClient {

    @Value("${clova.studio.url}")
    private String apiUrl;

    @Value("${clova.studio.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateEasyExplanation(String effect, String usage, String precautions, String emergencySigns) {
        String systemPrompt = "당신은 고령자를 위해 약 정보를 아주 쉬운 말로 설명해주는 도우미입니다. 3문장 이내의 짧은 문단으로만 답하세요.";
        String userPrompt = """
            다음 약 정보를 쉽게 설명해줘.
            효능: %s
            복용법: %s
            주의사항: %s
            이럴 땐 병원에 가야 함: %s
            """.formatted(effect, usage, precautions, emergencySigns);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "topP", 0.8,
                "topK", 0,
                "maxTokens", 200,
                "temperature", 0.5,
                "repetitionPenalty", 1.1
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl, new HttpEntity<>(body, headers), Map.class);

        // TODO: 아래 파싱은 콘솔 예제의 실제 응답 구조 확인 후 필드명 맞추기
        Map result = (Map) response.getBody().get("result");
        Map message = (Map) result.get("message");
        return (String) message.get("content");
    }
}