package com.example.medicines.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long medicineId;
    private LocalTime alarmTime;

    private boolean repeatDaily;

    @ElementCollection
    @CollectionTable(name = "alarm_repeat_days", joinColumns = @JoinColumn(name = "alarm_id"))
    @Column(name = "day_of_week")
    private List<String> repeatDays; // 예: ["MON", "WED", "FRI"], repeatDaily=false일 때만 사용
}