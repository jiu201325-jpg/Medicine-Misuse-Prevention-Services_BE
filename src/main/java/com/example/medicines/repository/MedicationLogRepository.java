package com.example.medicines.repository;

import com.example.medicines.entity.MedicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationLogRepository extends JpaRepository<MedicationLog, Long> {
    List<MedicationLog> findByUserId(Long userId);
    List<MedicationLog> findByUserIdAndMedicineId(Long userId, Long medicineId);
}
