package com.lemmiwinks.myscheduleserver.controller.web;

import com.lemmiwinks.myscheduleserver.entity.Appointment;
import com.lemmiwinks.myscheduleserver.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/schedule")
    public String schedule(Model model, Principal principal) {
        String username = principal.getName(); // получаем имя пользователя

        // Получаем не удаленные записи пользователя
        List<Appointment> appointments = appointmentRepository.findByUserNameAndDeletedFalseOrderByDateDesc(username);

        model.addAttribute("appointments", appointments); // Возвращаем на страницу
        return "schedule";
    }
}
