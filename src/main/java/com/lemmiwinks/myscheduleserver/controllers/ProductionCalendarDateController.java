package com.lemmiwinks.myscheduleserver.controllers;

import com.lemmiwinks.myscheduleserver.models.ProductionCalendarModel;
import com.lemmiwinks.myscheduleserver.services.CalendarDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendar")
public class ProductionCalendarDateController {

    @Autowired
    private CalendarDateService calendarDayService;
    private static final Logger logger = LoggerFactory.getLogger(ProductionCalendarController.class);

    @GetMapping("/get-date/{date}")
    public ResponseEntity<?> getDate(@PathVariable String date) {
        logger.info("DATE INPUT: {}", date);
        Optional<ProductionCalendarModel> productionCalendarModel = calendarDayService.getDay(date);

        if (productionCalendarModel.isPresent()) {
            return ResponseEntity.ok(productionCalendarModel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}