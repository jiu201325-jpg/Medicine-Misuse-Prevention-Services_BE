package com.example.medicines.repository;

import com.example.medicines.entity.GuardianRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuardianRelationRepository extends JpaRepository<GuardianRelation, Long> {
    List<GuardianRelation> findByGuardianId(Long guardianId);
    Optional<GuardianRelation> findByElderIdAndGuardianId(Long elderId, Long guardianId);
}