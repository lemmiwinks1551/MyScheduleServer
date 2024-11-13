package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.controller.rest.dto.AuthenticationRequestDto;
import com.lemmiwinks.myscheduleserver.entity.Appointment;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.AppointmentRepository;
import com.lemmiwinks.myscheduleserver.repository.ClientRepository;
import com.lemmiwinks.myscheduleserver.repository.ProcedureRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.AuthService;
import lombok.Data;
import lombok.val;
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

    @Autowired
    private AuthService authService;

    @PostMapping("/check-auth")
    public ResponseEntity<?> checkAuth(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return authService.checkAuth(authenticationRequestDto);
    }

    @PostMapping("/post-appointment")
    public ResponseEntity<?> postAppointment(@RequestBody Appointment appointment) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            appointmentRepository.save(appointment);
            Map<String, String> response = Map.of("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = Map.of("status", "error", "message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return null;
            }

            Date lastRemoteAppointmentTimestamp = appointmentRepository.findLastRemoteAppointmentTimestampByUsername(user.getUsername());

            return lastRemoteAppointmentTimestamp;
        } catch (Exception e) {
            return null;
        }
    }
}