package com.lemmiwinks.myscheduleserver.controllers;

import com.lemmiwinks.myscheduleserver.ProductionCalendar;
import com.lemmiwinks.myscheduleserver.ProductionCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ProductionCalendarController {

    @Autowired
    private ProductionCalendarRepository repository;

    @GetMapping("/")
    // Главная страница
    public String home(Model model) {
        model.addAttribute("title", "Главная страница"); // передаем данные в шаблон
        return "home"; // html-шаблон
    }

    @GetMapping("/first-note")
    // @GetMapping в Spring Boot используется для указания, что метод контроллера обрабатывает HTTP GET запросы.
    // Это означает, что данный метод будет вызван только при получении GET запроса на указанный в аннотации URL.
    public String getFirstNote(Model model) {
        Optional<ProductionCalendar> test = repository.findById(1);
        model.addAttribute("test", test.map(ProductionCalendar::getNote).orElse("Данные не найдены"));
        return "productionCalendar";
    }
}
