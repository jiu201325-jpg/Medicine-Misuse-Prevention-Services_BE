package com.example.medicines.service;

import com.example.medicines.dto.AlarmRequestDto;
import com.example.medicines.entity.Alarm;
import com.example.medicines.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public Alarm create(Long userId, AlarmRequestDto dto) {
        Alarm alarm = Alarm.builder()
                .userId(userId)
                .medicineId(dto.getMedicineId())
                .alarmTime(dto.getAlarmTime())
                .repeatDaily(dto.isRepeatDaily())
                .repeatDays(dto.getRepeatDays())
                .build();
        return alarmRepository.save(alarm);
    }

    public List<Alarm> getByUser(Long userId) {
        return alarmRepository.findByUserId(userId);
    }
}
