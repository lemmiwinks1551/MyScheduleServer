package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.entity.Appointment;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.AppointmentRepository;
import com.lemmiwinks.myscheduleserver.repository.ClientRepository;
import com.lemmiwinks.myscheduleserver.repository.ProcedureRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-data")
public class UserDataRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @PostMapping("/post-appointment")
    public ResponseEntity<Map<String, String>> postAppointment(@RequestBody Appointment appointment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "unauthorized"));
        }

        try {
            Appointment existingAppointment = appointmentRepository.findBySyncUUID(appointment.getSyncUUID());

            if (existingAppointment == null) {
                appointmentRepository.save(appointment);
                return ResponseEntity.ok(Map.of("status", "created"));
            }

            if (existingAppointment.getSyncTimestamp().before(appointment.getSyncTimestamp())) {
                appointmentRepository.save(appointment);
                return ResponseEntity.ok(Map.of("status", "updated"));
            } else {
                return ResponseEntity.ok(Map.of("status", "outdated"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping("/delete-appointment")
    public ResponseEntity<?> deleteAppointment(@RequestBody Appointment appointment) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            appointmentRepository.delete(appointment);
            Map<String, String> response = Map.of("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = Map.of("status", "error", "message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/get-remote-appointments")
    public List<Appointment> getUserRemoteAppointments(@RequestBody User user) {
/*        try {
            return appointmentRepository.findByUserName(user.getUsername());
        } catch (Exception e) {
            return null;
        }*/
        return null;
    }

    @PostMapping("/get-last-remote-appointment-timestamp")
    public Date getLastRemoteAppointmentTimestamp(@RequestBody User user) {
        // Возвращает дату последнего изменения в таблице пользователя
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return null;
            }

            return appointmentRepository.findLastRemoteAppointmentTimestampByUsername(user.getUsername());
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/get-remote-appointment-after-timestamp")
    public List<Appointment> getUserAppointmentsAfterTimestamp(@RequestBody Date timestamp) {
        // Возвращает список записей пользователя позднее указанной временной метки
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return null;
            }

            // Получаем имя пользователя
            String userName = auth.getName();

            List<Appointment> appointments = appointmentRepository.findAppointmentAfterTimestamp(userName, timestamp);

            return appointments;
        } catch (Exception e) {
            return null;
        }
    }
}