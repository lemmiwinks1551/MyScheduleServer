package com.lemmiwinks.myscheduleserver.controller;

import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        boolean error = false;

        if (userRepository.existsByUsername(userForm.getUsername())) {
            model.addAttribute("usernameError", "Пользователь с таким логином уже существует");
            error = true;
        }

        if (userForm.getUsername().length() < 4 || userForm.getUsername().length() > 16) {
            model.addAttribute("usernameError", "Логин пользователя должен содержать от 4 до 16 символов");
            error = true;
        }

        if (isInvalidUsername(userForm.getUsername())) {
            model.addAttribute("usernameError", "Логин не должен начинаться или заканчиваться символами: " +
                    "(., _, -). Содержать два и более специальных символов подряд или пробел.");
            error = true;
        }

        if (userRepository.existsByUserEmail(userForm.getUserEmail())) {
            model.addAttribute("userEmailError", "Пользователь с таким Email уже существует");
            error = true;
        }

        if (!userForm.getUserEmail().contains("@") || userForm.getUserEmail().isEmpty() || !userForm.getUserEmail().contains(".")) {
            model.addAttribute("userEmailError", "Указан некорректный Email");
            error = true;
        }

        if (userForm.getPassword().length() < 8 || userForm.getPassword().length() > 16) {
            model.addAttribute("passwordError", "Пароль пользователя должен содержать от 8 до 16 символов");
            error = true;
        }

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            error = true;
        }

        if (error) {
            // Возвращаем на ту же страницу с формой, передаем ошибки
            return "registration";
        }

        // Если ошибок нет, регистрируем пользователя
        registerUser(userForm);

        model.addAttribute("message", "Ссылка для верификации аккаунта отправлена на почтовый ящик, указанный при регистрации");
        model.addAttribute("message-type", "success-message");
        return "message";
    }


    public void registerUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token") String confirmationToken, Model model) {
        model.addAttribute("title", "Верификация аккаунта");

        if (userService.confirmEmail(confirmationToken)) {
            model.addAttribute("message", "Поздравляем! Ваш аккаунт подтвержден");
            model.addAttribute("message-type", "success-message");
        } else {
            model.addAttribute("message", "Не удалось подтвердить аккаунт");
            model.addAttribute("message-type", "error-message");
        }
        return "message";
    }


    public static boolean isInvalidUsername(String username) {
        String USERNAME_PATTERN = "^(?!.*[._-]{2,})[a-zA-Zа-яА-ЯёЁ0-9][a-zA-Zа-яА-ЯёЁ0-9._-]*[a-zA-Zа-яА-ЯёЁ0-9]$";

        // Создаем объект Pattern для регулярного выражения
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);

        // Проверяем, соответствует ли строка регулярному выражению
        boolean matches = pattern.matcher(username).matches();

        // Возвращаем true, если строка НЕ соответствует регулярному выражению
        return !matches;
    }
}