package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.entity.CalendarDate;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.CalendarDateRepository;
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
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/calendar-color")
public class CalendarColorRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarDateRepository calendarDateRepository;

    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> post(@RequestBody CalendarDate calendarDate) {
        try {
            CalendarDate existingCalendarDate = calendarDateRepository.findBySyncUUID(calendarDate.getSyncUUID());

            if (existingCalendarDate == null) {
                // Если запись не существует -> новую создать
                calendarDateRepository.save(calendarDate);
                return ResponseEntity.ok(Map.of("status", "created"));
            }

            if (Objects.equals(existingCalendarDate.getSyncStatus(), "DELETED")) {
                // Если запись была удалена на сервере и уже стоит отметка об этом - отклоняем обновление
                // и обновляем время синхронизации для удаления с устройств, где синхронизация не была произведена
                existingCalendarDate.setSyncTimestamp(new Date().getTime());
                calendarDateRepository.save(existingCalendarDate);
                return ResponseEntity.ok(Map.of("status", "rejected"));
            }

            if (existingCalendarDate.getSyncTimestamp() < calendarDate.getSyncTimestamp()) {
                // Если запись уже существует -> обновить старую, если входящая временная метка позднее, чем актуальная
                calendarDateRepository.save(calendarDate);
                return ResponseEntity.ok(Map.of("status", "updated"));
            } else {
                return ResponseEntity.ok(Map.of("status", "outdated"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody CalendarDate calendarDate) {
        try {
            // Устанавливаем в запись статус DELETED
            calendarDate.setSyncStatus("DELETED");

            // Устанавливаем в запись время удаления, чтобы на других устройствах она обновилась и удалилась
            calendarDate.setSyncTimestamp(new Date().getTime());

            calendarDateRepository.save(calendarDate);

            Map<String, String> response = Map.of("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = Map.of("status", "error", "message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/get-last-timestamp")
    public Long getLastRemoteTimestamp(@RequestBody User user) {
        // Возвращает дату последнего изменения в таблице CalendarColor
        try {
            Long lastTimestamp = calendarDateRepository.findLastRemoteCalendarDateTimestampByUsername(user.getUsername());
            return lastTimestamp;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/get-remote-appointment-after-timestamp")
    public List<CalendarDate> getAfterTimestamp(@RequestBody Long timestamp) {
        // Возвращает список записей пользователя позднее указанной временной метки
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return null;
            }

            // Получаем имя пользователя
            String userName = auth.getName();

            List<CalendarDate> calendarDates = calendarDateRepository.findCalendarDateAfterTimestamp(userName, timestamp);

            return calendarDates;
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

            Long count = calendarDateRepository.getCountByUser(userName);

            return count;
        } catch (Exception e) {
            return 0L;
        }
    }
}
