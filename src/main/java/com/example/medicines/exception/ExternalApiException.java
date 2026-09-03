package com.example.medicines.exception;

import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException {
    private final String errorCode;

    public ExternalApiException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
