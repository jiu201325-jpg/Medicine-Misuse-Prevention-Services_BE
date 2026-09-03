package com.example.medicines.service;

import com.example.medicines.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClovaVoiceService {

    @Value("${clova.voice.url}")
    private String voiceUrl;

    @Value("${clova.voice.client-id}")
    private String clientId;

    @Value("${clova.voice.client-secret}")
    private String clientSecret;

    @Value("${audio.storage.path}")
    private String storagePath; // 로컬 저장 or S3

    private final RestTemplate restTemplate;

    public String generateAudioUrl(String text) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
            headers.set("X-NCP-APIGW-API-KEY", clientSecret);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("speaker", "nara");
            body.add("text", text);
            body.add("speed", "-1"); // 어르신 대상이면 살짝 느리게 추천

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    voiceUrl, HttpMethod.POST, entity, byte[].class
            );

            String fileName = UUID.randomUUID() + ".mp3";
            Path savedPath = Paths.get(storagePath, fileName);
            Files.write(savedPath, response.getBody());

            return "/audio/" + fileName; // 실제 서빙 경로로 채움

        } catch (ResourceAccessException e) {
            throw new ExternalApiException("TTS_GENERATION_FAILED", "TTS 서버 응답 지연", e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ExternalApiException("TTS_GENERATION_FAILED", "TTS API 오류: " + e.getStatusCode(), e);
        } catch (IOException e) {
            throw new ExternalApiException("TTS_GENERATION_FAILED", "음성 파일 저장 실패", e);
        }
    }
}
