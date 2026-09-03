package com.example.medicines.service;

import com.example.medicines.dto.OcrResponse;
import com.example.medicines.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClovaOcrService {

    @Value("${clova.ocr.url}")
    private String ocrUrl;

    @Value("${clova.ocr.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate; // 또는 WebClient

    public String extractMedicationName(MultipartFile image) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-OCR-SECRET", secretKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = buildOcrRequest(image);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<OcrResponse> response = restTemplate.exchange(
                    ocrUrl, HttpMethod.POST, entity, OcrResponse.class
            );

            return parseMedicationNameFromOcr(response.getBody());

        } catch (ResourceAccessException e) { // 타임아웃/연결 실패
            throw new ExternalApiException("OCR_FAILED", "OCR 서버 응답 지연", e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ExternalApiException("OCR_FAILED", "OCR API 오류: " + e.getStatusCode(), e);
        } catch (Exception e) {
            throw new ExternalApiException("OCR_FAILED", "이미지 인식 실패", e);
        }
    }

    private Map<String, Object> buildOcrRequest(MultipartFile image) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

        Map<String, Object> imageInfo = new HashMap<>();
        imageInfo.put("format", "jpg"); // 실제 확장자에 맞게, 혹은 image.getContentType()에서 추출
        imageInfo.put("name", "medicine");
        imageInfo.put("data", base64Image);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("version", "V2");
        requestBody.put("requestId", UUID.randomUUID().toString());
        requestBody.put("timestamp", System.currentTimeMillis());
        requestBody.put("images", List.of(imageInfo));

        return requestBody;
    }

    private String parseMedicationNameFromOcr(OcrResponse response) {
        if (response == null || response.getImages() == null || response.getImages().isEmpty()) {
            throw new ExternalApiException("OCR_FAILED", "OCR 인식 결과가 비어있음", null);
        }

        List<OcrResponse.Field> fields = response.getImages().get(0).getFields();
        if (fields == null || fields.isEmpty()) {
            throw new ExternalApiException("OCR_FAILED", "텍스트를 인식하지 못함", null);
        }

        // 인식된 텍스트 조각들을 하나로 합침 (실제로는 여기서 약품명 DB와 유사도 매칭 필요)
        return fields.stream()
                .map(OcrResponse.Field::getInferText)
                .reduce("", (a, b) -> a + b);
    }
}
