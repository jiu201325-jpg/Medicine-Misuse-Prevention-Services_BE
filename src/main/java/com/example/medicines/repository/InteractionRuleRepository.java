package com.example.medicines.repository;

import com.example.medicines.entity.InteractionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InteractionRuleRepository extends JpaRepository<InteractionRule, Long> {

    @Query("SELECT r FROM InteractionRule r " +
            "WHERE (r.drugAId = :idA AND r.drugBId = :idB) " +
            "OR (r.drugAId = :idB AND r.drugBId = :idA)")
    List<InteractionRule> findByDrugPair(@Param("idA") Long idA, @Param("idB") Long idB);
}