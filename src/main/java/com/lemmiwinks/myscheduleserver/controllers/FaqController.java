package com.lemmiwinks.myscheduleserver.controllers;

import com.lemmiwinks.myscheduleserver.models.FaqModel;
import com.lemmiwinks.myscheduleserver.models.ProductionCalendarModel;
import com.lemmiwinks.myscheduleserver.repositiry.FaqRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @Autowired
    private FaqRepository faqRepository;
    private static final Logger logger = LoggerFactory.getLogger(FaqController.class);

    @GetMapping("/faq")
    public String faq(Model model) {
        logger.info("Вызван метод faqModels");

        Iterable<FaqModel> faqModels = faqRepository.findAll();

        logger.info(faqRepository.toString());

        for (FaqModel item : faqModels) {
            logger.info(item.toString());
        }

        model.addAttribute("faqModels", faqModels);
        return "faqModels";
    }
}
