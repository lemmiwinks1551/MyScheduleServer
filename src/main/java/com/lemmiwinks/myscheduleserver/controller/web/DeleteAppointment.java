package com.lemmiwinks.myscheduleserver.controller.web;

import com.lemmiwinks.myscheduleserver.entity.Appointment;
import com.lemmiwinks.myscheduleserver.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class DeleteAppointment {

    @Autowired
    AppointmentRepository appointmentRepository;

    @PostMapping("/deleteAppointment")
    public String markAppointmentAsDeleted(@RequestParam("syncUUID") String syncUUID) {
        Appointment appointment = appointmentRepository.findBySyncUUID(syncUUID);

        if (appointment != null) {
            appointment.setSyncStatus("DELETED");
            appointment.setSyncTimestamp(new Date().getTime());
            appointmentRepository.save(appointment);
        }

        return "redirect:/schedule";
    }

}
