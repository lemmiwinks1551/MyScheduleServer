package com.lemmiwinks.myscheduleserver.repositiry;

import com.lemmiwinks.myscheduleserver.models.ProductionCalendarModel;
import org.springframework.data.jpa.repository.JpaRepository;

// интерфейс для работы с таблицей production_calendar
public interface ProductionCalendarRepository extends JpaRepository<ProductionCalendarModel, Integer> {
}
