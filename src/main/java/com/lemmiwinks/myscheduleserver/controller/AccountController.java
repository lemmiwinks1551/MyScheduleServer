package com.lemmiwinks.myscheduleserver.controller;

import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/account")
    public String account(Model model) {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Извлекаем имя пользователя из аутентифицированного объекта
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername());

        // Заполняем модель атрибутами для отображения на странице
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userEmail", user.getUserEmail());
        if (user.isEnabled()) {
            model.addAttribute("isVerified", "Аккаунт подтвержден");
            model.addAttribute("verificationColor", "green");
        } else {
            model.addAttribute("isVerified", "Аккаунт не подтвержден! Перейдите по ссылке в письме, чтобы активировать аккаунт.");
            model.addAttribute("verificationColor", "red");
        }

        return "account";
    }
}
