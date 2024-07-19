package com.lemmiwinks.myscheduleserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    // Главная страница
    public String home(Model model) {
        model.addAttribute("title", "Главная страница"); // передаем данные в шаблон
        return "home"; // html-шаблон
    }
}
