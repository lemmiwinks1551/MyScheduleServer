package com.lemmiwinks.myscheduleserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/production-calendar")
public class ProductionCalendarController {

    @Autowired
    private ProductionCalendarRepository repository;

    @GetMapping("/first-note")
    public String getFirstNote() {
        Optional<ProductionCalendar> firstRecord = repository.findById(1); // предполагается, что первая запись имеет id=1
        return firstRecord.map(ProductionCalendar::getNote).orElse("No data found");
    }
}
