package com.lemmiwinks.myscheduleserver.services;

import com.lemmiwinks.myscheduleserver.models.ProductionCalendarModel;
import com.lemmiwinks.myscheduleserver.repositiry.ProductionCalendarDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CalendarDateService {

    @Autowired
    private ProductionCalendarDateRepository productionCalendarDateRepository;

    public Optional<ProductionCalendarModel> getDay(String date) {
        return productionCalendarDateRepository.findByDate(date);
    }
}