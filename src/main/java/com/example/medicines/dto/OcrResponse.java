package com.example.medicines.dto;

import lombok.Data;
import java.util.List;

@Data
public class OcrResponse {
    private List<Image> images;

    @Data
    public static class Image {
        private String message;
        private List<Field> fields;
    }

    @Data
    public static class Field {
        private String inferText;
        private String inferConfidence;
    }
}
