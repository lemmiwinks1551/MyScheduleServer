package com.lemmiwinks.myscheduleserver.repositiry;

import com.lemmiwinks.myscheduleserver.models.ProductionCalendarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductionCalendarDateRepository extends JpaRepository<ProductionCalendarModel, Integer> {
    Optional<ProductionCalendarModel> findByDate(String date);
}

