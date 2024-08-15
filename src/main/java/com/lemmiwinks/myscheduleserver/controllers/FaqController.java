package com.lemmiwinks.myscheduleserver.controllers;

import com.lemmiwinks.myscheduleserver.models.FaqModel;
import com.lemmiwinks.myscheduleserver.repositiry.FaqRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faq")
public class FaqController {

    @Autowired
    private FaqRepository faqRepository;

    private static final Logger logger = LoggerFactory.getLogger(FaqController.class);

    @GetMapping()
    public List<FaqModel> faq() {
        logger.info("Вызван метод faqModels");

        List<FaqModel> faqModels = faqRepository.findAll();

        logger.info(faqRepository.toString());

        for (FaqModel item : faqModels) {
            logger.info(item.toString());
        }

        return faqModels;
    }
}
