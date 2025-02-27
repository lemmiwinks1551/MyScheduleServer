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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        try {
            Appointment existingAppointment = appointmentRepository.findBySyncUUID(appointment.getSyncUUID());

            // Заглушка, пока не настроена синхронизация клиентов - обнуляем поле clientID
            appointment.setClientId(null);

            if (existingAppointment == null) {
                // Если запись не существует -> новую создать
                appointmentRepository.save(appointment);
                return ResponseEntity.ok(Map.of("status", "created"));
            }

            if (Objects.equals(existingAppointment.getSyncStatus(), "DELETED")) {
                // Если запись была удалена на сервере и уже стоит отметка об этом - отклоняем обновление
                // и обновляем время синхронизации для удаления с устройств, где синхронизация не была произведена
                existingAppointment.setSyncTimestamp(new Date().getTime());
                appointmentRepository.save(existingAppointment);
                return ResponseEntity.ok(Map.of("status", "rejected"));
            }

            if (existingAppointment.getSyncTimestamp() < appointment.getSyncTimestamp()) {
                // Если запись уже существует -> обновить старую, если входящая временная метка позднее, чем актуальная
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
            // Устанавливаем в запись статус DELETED
            appointment.setSyncStatus("DELETED");

            // Устанавливаем в запись время удаления, чтобы на других устройствах она обновилась и удалилась
            // Логика такая нужна, чтобы если удаление было раньше изменения записи - удаление было отправлено на остальные устройства
            appointment.setSyncTimestamp(new Date().getTime());

            appointmentRepository.save(appointment);

            Map<String, String> response = Map.of("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = Map.of("status", "error", "message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/get-last-remote-appointment-timestamp")
    public Long getLastRemoteAppointmentTimestamp(@RequestBody User user) {
        // Возвращает дату последнего изменения в таблице пользователя
        try {
            Long lastTimestamp = appointmentRepository.findLastRemoteAppointmentTimestampByUsername(user.getUsername());
            if (lastTimestamp == null) {
                // Если записей нет - возвращаем 0, чтобы спровоцировать полную синхронизацию от пользователя
                lastTimestamp = 0L;
            }
            return lastTimestamp;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/get-remote-appointment-after-timestamp")
    public List<Appointment> getUserAppointmentsAfterTimestamp(@RequestBody Long timestamp) {
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

    @PostMapping("/get-count")
    public Long getCount() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return null;
            }

            // Получаем имя пользователя
            String userName = auth.getName();

            Long count = appointmentRepository.getCountByUser(userName);

            return count;
        } catch (Exception e) {
            return 0L;
        }
    }

    @PostMapping("/enable-sync")
    public Boolean enableSync(@RequestBody User user) {
        try {
            User userFromRepository = userRepository.findByUsername(user.getUsername());
            userFromRepository.setSyncEnabled(true);
            userRepository.save(userFromRepository);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/disable-sync")
    public Boolean disableSync(@RequestBody User user) {
        try {
            User userFromRepository = userRepository.findByUsername(user.getUsername());
            userFromRepository.setSyncEnabled(false);
            userRepository.save(userFromRepository);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}