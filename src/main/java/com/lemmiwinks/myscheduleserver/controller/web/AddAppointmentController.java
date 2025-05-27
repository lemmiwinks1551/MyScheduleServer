package com.lemmiwinks.myscheduleserver.controller.web;

import com.lemmiwinks.myscheduleserver.entity.Appointment;
import com.lemmiwinks.myscheduleserver.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Controller
public class AddAppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/addAppointment")
    public String addAppointment() {
        return "addAppointment";
    }

    @PostMapping("/createAppointment")
    public String createAppointment(
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
            Model model,
            Principal principal
    ) {
        Appointment appointment = new Appointment();

        // Sync
        appointment.setSyncUUID(UUID.randomUUID().toString());
        appointment.setUserName(principal.getName());
        appointment.setSyncTimestamp(new Date().getTime());
        appointment.setSyncStatus("NotSynchronized");

        // Appointment
        appointment.setDate(LocalDate.parse(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        appointment.setTime(time);
        appointment.setNotes(appointmentNotes);
        appointment.setDeleted(false); // по умолчанию

        // Client
        appointment.setName(clientName);
        appointment.setPhone(phone);
        appointment.setTelegram(telegram);
        appointment.setInstagram(instagram);
        appointment.setVk(vk);
        appointment.setWhatsapp(whatsapp);
        appointment.setClientNotes(clientNotes);
        appointment.setPhoto(null);

        // Procedure
        appointment.setProcedure(procedure);
        appointment.setProcedurePrice(price);
        appointment.setProcedureNotes(null);

        appointmentRepository.save(appointment);

        model.addAttribute("message", "Запись добавлена!");
        return "message";
    }
}
