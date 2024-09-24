package com.lemmiwinks.myscheduleserver.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Util {
    public static boolean isTokenExpired(Date date, int hours) {
        // Конвертируем
        LocalDateTime tokenCreatedDateTime = convertToLocalDateTime(date);

        // Получаем текущее время
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Вычисляем разницу во времени между текущим временем и датой создания токена
        Duration duration = Duration.between(tokenCreatedDateTime, currentDateTime);

        // Проверяем, прошло ли больше n часов
        return duration.toHours() > hours;
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
