package com.lemmiwinks.myscheduleserver.controller.web;

import com.lemmiwinks.myscheduleserver.entity.Appointment;
import com.lemmiwinks.myscheduleserver.entity.PremiumUser;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.AppointmentRepository;
import com.lemmiwinks.myscheduleserver.repository.PremiumUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ScheduleController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PremiumUserRepository premiumUserRepository;

    @GetMapping("/schedule")
    public String schedule(Model model,
                           Principal principal,
                           @AuthenticationPrincipal User user) {

        Optional<PremiumUser> premiumUser = premiumUserRepository.findByUser(user);

        boolean isPremium = premiumUser.map(PremiumUser::getIsPremium).orElse(false);

        if (!isPremium) {
            model.addAttribute("message", "Только для премиум пользователей. Купите премиум в приложении 'Запись клиентов'");
            return "message";
        }

        String username = principal.getName(); // получаем имя пользователя

        // Получаем не удаленные записи пользователя
        List<Appointment> appointments = appointmentRepository.findByUserNameAndDeletedFalseOrderByDateTimeDesc(username);

        model.addAttribute("appointments", appointments); // Возвращаем на страницу
        return "schedule";
    }
}
