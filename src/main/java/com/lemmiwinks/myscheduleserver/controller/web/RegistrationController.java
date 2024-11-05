package com.lemmiwinks.myscheduleserver.controller.web;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Value("${SMARTCAPTCHA_SERVER_KEY}")
    private String SMARTCAPTCHA_SERVER_KEY;

    @Value("${server.port}")
    private String port;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm,
                          @RequestParam("smart-token") String captchaToken,
                          Model model) {
        boolean error = false;

        // Проверяем капчу (не проверяем локально)
        if (!captchaPassed(captchaToken)) {
            model.addAttribute("captchaError", "Проверка капчи не пройдена. Попробуйте снова.");
            error = true;
        }

        if (userRepository.existsByUsername(userForm.getUsername())) {
            model.addAttribute("usernameError", "Пользователь с таким логином уже существует");
            error = true;
        }

        if (userForm.getUsername().length() < 4 || userForm.getUsername().length() > 16) {
            model.addAttribute("usernameError", "Логин пользователя должен содержать от 4 до 16 символов");
            error = true;
        }

        if (isInvalidUsername(userForm.getUsername())) {
            model.addAttribute("usernameError", "Логин не должен начинаться или заканчиваться символами: " + "(., _, -). Содержать два и более специальных символов подряд или пробел.");
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

        try {
            ConfirmationToken confirmationTokenEntity = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

            // Проверяем, существует ли такой ConfirmationToken
            if (confirmationTokenEntity == null) {
                model.addAttribute("message", "Токен не найден или его срок действия истек");
                return "message";
            }

            // Получаем юзера по токену
            Optional<User> userOptional = userRepository.findById(confirmationTokenEntity.getUser().getId());

            // Проверяем, существует ли пользователь
            if (!userOptional.isPresent()) {
                model.addAttribute("message", "Пользователь не найден");
                return "message";
            }

            User user = userOptional.get();

            // Проверяем, существует ли пользователь
            if (user.getEmailVerified()) {
                model.addAttribute("message", "Аккаунт уже был подтвержден");
                return "message";
            }

            // Подтверждение аккаунта
            user.setEmailVerified(true);
            userService.updateUser(user);

            // Удаляем токен после успешного подтверждения аккаунта пользователя
            confirmationTokenRepository.delete(confirmationTokenEntity);

            model.addAttribute("message", "Поздравляем! Ваш аккаунт подтвержден");
        } catch (Exception e) {
            model.addAttribute("message", "Не удалось подтвердить аккаунт. Возникла непредвиденная ошибка.");
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

    private boolean captchaPassed(String captchaToken) {
        String url = "https://smartcaptcha.yandexcloud.net/validate";
        RestTemplate restTemplate = new RestTemplate();

        // Подготовка заголовков запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Подготовка тела запроса
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("secret", SMARTCAPTCHA_SERVER_KEY);
        requestBody.add("token", captchaToken);

        // Объединяем заголовки и тело запроса
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Отправляем запрос с заголовками и телом
        Map<String, Object> response = restTemplate.postForObject(url, requestEntity, Map.class);

        if ("ok".equals(response.get("status"))) {
            return true;
        } else {
            return false;
        }
    }
}