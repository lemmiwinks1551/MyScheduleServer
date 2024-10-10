package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.entity.UserEventModel;
import com.lemmiwinks.myscheduleserver.repository.UserEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // указывает, что класс является контроллером и возвращает JSON-ответы
@RequestMapping("/api/user-events") // задает базовый URL для всех методов в этом контроллере.
public class UserEventController {
    @Autowired
    private UserEventRepository userEventRepository;

    @PostMapping // указывает, что метод будет обрабатывать POST-запросы.
    public ResponseEntity<UserEventModel> createUserEvent(@RequestBody UserEventModel userEventModel) { // используется для связывания тела запроса с объектом UserEventModel.
        UserEventModel savedEvent = userEventRepository.save(userEventModel);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }
}
