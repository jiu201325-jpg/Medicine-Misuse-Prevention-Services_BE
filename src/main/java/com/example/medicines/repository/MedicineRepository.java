package com.example.medicines.repository;

import com.example.medicines.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // 성분명 또는 상품명(1~3) 중 하나라도 검색어를 포함하면 매칭
    @Query("""
        SELECT DISTINCT m FROM Medicine m LEFT JOIN m.productNames p
        WHERE m.ingredient LIKE %:query% OR p LIKE %:query%
        """)
    List<Medicine> searchByNameOrIngredient(@Param("query") String query);
}
