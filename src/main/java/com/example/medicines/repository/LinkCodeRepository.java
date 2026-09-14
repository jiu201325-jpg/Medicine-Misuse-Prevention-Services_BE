package com.example.medicines.repository;

import com.example.medicines.entity.LinkCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkCodeRepository extends JpaRepository<LinkCode, Long> {
    Optional<LinkCode> findByCode(String code);
}