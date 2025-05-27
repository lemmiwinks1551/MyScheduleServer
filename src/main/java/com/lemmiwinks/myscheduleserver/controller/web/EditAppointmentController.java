package com.lemmiwinks.myscheduleserver.controller.web;

import com.lemmiwinks.myscheduleserver.entity.Appointment;
import com.lemmiwinks.myscheduleserver.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class EditAppointmentController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/editAppointment")
    public String editAppointmentPage(@RequestParam("syncUUID") String syncUUID, Model model) {
        Appointment appointment = appointmentRepository.findBySyncUUID(syncUUID);
        if (appointment == null) {
            return "redirect:/appointments";
        }

        // Преобразуем дату в формат для input[type=date]
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = LocalDate.parse(appointment.getDate(), inputFormatter)
                .format(outputFormatter);

        model.addAttribute("appointment", appointment);
        model.addAttribute("formattedDate", formattedDate);
        return "editAppointment";
    }

    @PostMapping("/updateAppointment")
    public String updateAppointment(
            @RequestParam("syncUUID") String syncUUID,
            @RequestParam("day") String day,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "clientName", required = false) String clientName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "vk", required = false) String vk,
            @RequestParam(value = "telegram", required = false) String telegram,
            @RequestParam(value = "instagram", required = false) String instagram,
            @RequestParam(value = "whatsapp", required = false) String whatsapp,
            @RequestParam(value = "clientNotes", required = false) String clientNotes,
            @RequestParam(value = "procedure", required = false) String procedure,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "appointmentNotes", required = false) String appointmentNotes,
            Model model
    ) {
        Appointment appointment = appointmentRepository.findBySyncUUID(syncUUID);
        if (appointment == null) {
            model.addAttribute("message", "Ошибка: запись не найдена.");
            return "message";
        }

        // Обновляем поля
        appointment.setDate(LocalDate.parse(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        appointment.setTime(time);
        appointment.setNotes(appointmentNotes);

        appointment.setName(clientName);
        appointment.setPhone(phone);
        appointment.setTelegram(telegram);
        appointment.setInstagram(instagram);
        appointment.setVk(vk);
        appointment.setWhatsapp(whatsapp);
        appointment.setClientNotes(clientNotes);

        appointment.setProcedure(procedure);
        appointment.setProcedurePrice(price);

        // Обновляем syncTimestamp и syncStatus
        appointment.setSyncTimestamp(new Date().getTime());
        appointment.setSyncStatus("NotSynchronized");

        appointmentRepository.save(appointment);

        model.addAttribute("message", "Запись изменена.");
        return "message";
    }
}
