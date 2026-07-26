package com.example.medicines.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MISSING_IMAGE(HttpStatus.BAD_REQUEST, "이미지 파일이 필요합니다."),
    OCR_FAILED(HttpStatus.BAD_GATEWAY, "이미지에서 텍스트를 인식하지 못했습니다."),
    MEDICINE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 약 정보를 찾을 수 없습니다."),
    INVALID_QUERY(HttpStatus.BAD_REQUEST, "검색어를 입력해주세요."),
    TTS_GENERATION_FAILED(HttpStatus.BAD_GATEWAY, "음성 생성 중 오류가 발생했습니다."),
    LLM_GENERATION_FAILED(HttpStatus.BAD_GATEWAY, "설명 생성 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}