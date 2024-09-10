package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.ProductionCalendarModel;
import org.springframework.data.jpa.repository.JpaRepository;

// интерфейс для работы с таблицей production_calendar
public interface ProductionCalendarRepository extends JpaRepository<ProductionCalendarModel, Integer> {
}
