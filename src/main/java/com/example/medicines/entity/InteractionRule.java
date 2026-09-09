package com.example.medicines.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class InteractionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long drugAId;
    private Long drugBId;

    private String riskLevel; // 예: "HIGH", "MEDIUM"

    @Column(length = 1000)
    private String warningMessage;
}