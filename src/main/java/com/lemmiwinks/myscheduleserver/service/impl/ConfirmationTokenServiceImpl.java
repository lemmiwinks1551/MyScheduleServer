package com.lemmiwinks.myscheduleserver.service.impl;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.service.ConfirmationTokenService;
import com.lemmiwinks.myscheduleserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    EmailService emailService;

    @Override
    public void saveToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void updateCreatedDate(ConfirmationToken confirmationToken) {
        // Обновить дату создания токена
        confirmationToken.setCreatedDate(new Date());
    }

    @Override
    public void updateToken(ConfirmationToken confirmationToken) {
        // Установить новый токен
        confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
        updateCreatedDate(confirmationToken);
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void sendVerificationEmail(User user) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByUserId(user.getId());

        String verificationLink = "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken();

        String htmlSubject = "Приложение Запись клиентов - завершение регистрации";
        String htmlContent = "Добро пожаловать, <b>" + user.getUsername() + "</b>!"
                + "<br>Чтобы завершить регистрацию, "
                + "<a href='" + verificationLink + "'>перейдите по ссылке</a>."
                + "<br>Ссылка действительна 24 часа.<br><br>"
                + "<hr style='border:none; border-top:1px solid #ccc;'/>"
                + "<p style='font-size:14px; color:#333;'>"
                + "С уважением,<br>"
                + "<b>Команда приложения Запись клиентов</b><br>"
                + "<span style='font-size:12px; color:#888;'>"
                + "Пожалуйста, не отвечайте на это письмо.<br>"
                + "Для связи с нами используйте контактные данные на нашем сайте."
                + "</span></p>";

        try {
            emailService.sendEmailWithHtml(user.getUserEmail(), htmlSubject, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}