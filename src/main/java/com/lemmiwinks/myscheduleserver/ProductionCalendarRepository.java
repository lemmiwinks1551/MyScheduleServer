package com.lemmiwinks.myscheduleserver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionCalendarRepository extends JpaRepository<ProductionCalendar, Integer> {
}
