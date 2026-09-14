package com.example.medicines.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class GuardianRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long elderId;
    private Long guardianId;

    private boolean canViewMedicationLog;
    private boolean canReceiveAlerts;
    private boolean canEmergencyContact;
}