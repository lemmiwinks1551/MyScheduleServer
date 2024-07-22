package com.lemmiwinks.myscheduleserver.controllers;

import com.lemmiwinks.myscheduleserver.models.ProductionCalendarModel;
import com.lemmiwinks.myscheduleserver.repositiry.ProductionCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calendar")
public class ProductionCalendarYearController {

    @Autowired
    private ProductionCalendarRepository productionCalendarRepository;

    @GetMapping("/get-year/{year}")
    public List<ProductionCalendarModel> getYearDates(@PathVariable String year) {
        // Извлечение всех записей из базы данных
        List<ProductionCalendarModel> allRecords = productionCalendarRepository.findAll();

        // Фильтрация записей, содержащих указанный год в поле date
        return allRecords.stream()
                .filter(record -> {
                    // Проверяем, содержит ли поле date указанный год
                    return record.getDate().contains(year);
                })
                .collect(Collectors.toList());
    }
}

