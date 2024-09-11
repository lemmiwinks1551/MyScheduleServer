package com.lemmiwinks.myscheduleserver.controller;

import com.lemmiwinks.myscheduleserver.entity.FaqModel;
import com.lemmiwinks.myscheduleserver.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/faq")
public class FaqWebController {
    @Autowired
    private FaqRepository faqRepository;

    @GetMapping("/view")
    public String viewFaq(Model model) {

        // Получаем данные из базы
        List<FaqModel> faqModels = faqRepository.findAll();

        // Добавляем данные в модель для отображения на странице
        model.addAttribute("faqModels", faqModels);

        // Возвращаем имя JSP страницы, которая будет отображена
        return "faq";
    }
}
