package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.entity.dto.RegistrationRequestDto;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/registration")
public class Registration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Map<Object, Object>> addUser(@RequestBody RegistrationRequestDto registrationRequestDto) {
        Map<Object, Object> response = new HashMap<>();

        // Проверка существования пользователя
        if (userRepository.existsByUsername(registrationRequestDto.getUsername())) {
            response.put("status", "Пользователь с таким логином уже существует");
            return ResponseEntity.ok(response);
        }

        if (userRepository.existsByUserEmail(registrationRequestDto.getEmail())) {
            response.put("status", "Пользователь с таким Email уже существует");
            return ResponseEntity.ok(response);
        }

        try {
            // Создаем нового пользователя
            User user = new User();
            user.setUsername(registrationRequestDto.getUsername());
            user.setUserEmail(registrationRequestDto.getEmail());
            user.setPassword(registrationRequestDto.getPassword());

            // Регистрируем пользователя
            userService.saveUser(user);

            response.put("status", "Пользователь успешно зарегистрирован");
            return ResponseEntity.ok(response); // 200 OK
        } catch (Exception e) {
            response.put("status", "Возникла непредвиденная ошибка");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}