package com.example.medicines.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AudioCache {

    @Id
    private Long drugId; // 약물 ID를 PK로 사용

    @Column(nullable = false)
    private String audioUrl; // Python 서버에서 생성된 오디오 파일 경로 또는 URL

    @Column(nullable = false)
    private LocalDateTime generatedAt;
}
