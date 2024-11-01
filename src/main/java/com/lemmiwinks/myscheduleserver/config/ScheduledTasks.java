package com.lemmiwinks.myscheduleserver.config;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.PasswordResetToken;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    // Расписание удаляет expired токены
    // чтобы пользователи смогли запрашивать удаление пароля и подтверждение почты каждые час и 24 часа соответственно
    // в БД можно не создавать правила для удаления

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Scheduled(fixedRate = 3600 * 1000) // Расписание запускается 1 раз в час
    public void schedule() {
        deleteExpiredConfirmationTokens();
        deleteExpiredPasswordTokens();
    }

    private void deleteExpiredConfirmationTokens() {
        List<ConfirmationToken> confirmationTokens = confirmationTokenRepository.findAll();
        Date oneDayAgo = new Date(System.currentTimeMillis() - 24 * 3600 * 1000); // 24 часа

        for (ConfirmationToken token : confirmationTokens) {
            if (token.getCreatedDate().before(oneDayAgo)) {
                confirmationTokenRepository.delete(token);
            }
        }
    }

    private void deleteExpiredPasswordTokens() {
        List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findAll();
        Date oneHourAgo = new Date(System.currentTimeMillis() - 3600 * 1000); // 1 час

        for (PasswordResetToken token : passwordResetTokens) {
            if (token.getCreatedDate().before(oneHourAgo)) {
                passwordResetTokenRepository.delete(token);
            }
        }
    }
}