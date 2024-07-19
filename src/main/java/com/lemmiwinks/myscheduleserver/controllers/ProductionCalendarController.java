package com.lemmiwinks.myscheduleserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lemmiwinks.myscheduleserver.models.ProductionCalendarModel;
import com.lemmiwinks.myscheduleserver.repositiry.ProductionCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductionCalendarController {

    @Autowired // автосвязывание с репозиторием
    private ProductionCalendarRepository productionCalendarRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductionCalendarController.class);

    @GetMapping("/production_calendar")
    // данный метод будет вызван только при получении GET запроса на указанный в аннотации URL
    public String productionCalendar(Model model) {
        logger.info("Вызван метод productionCalendar");

        Iterable<ProductionCalendarModel> productionCalendarRepositoryAll = productionCalendarRepository.findAll();

        logger.info(productionCalendarRepositoryAll.toString());

        for (ProductionCalendarModel item : productionCalendarRepositoryAll) {
            logger.info(item.toString());
        }

        model.addAttribute("productionCalendarRepositoryAll", productionCalendarRepositoryAll);
        return "productionCalendar";
    }
}