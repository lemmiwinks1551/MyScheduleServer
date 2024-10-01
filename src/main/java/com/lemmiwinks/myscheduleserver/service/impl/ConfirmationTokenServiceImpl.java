package com.lemmiwinks.myscheduleserver.service.impl;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.service.ConfirmationTokenService;
import com.lemmiwinks.myscheduleserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${web.url}")
    private String webUrl;

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

        String verificationLink = webUrl + "/confirm-account?token=" + confirmationToken.getConfirmationToken();

        String htmlSubject = "Приложение Запись клиентов - Подтверждение учетной записи";

        String htmlContent = "<div style='background: linear-gradient(135deg, #6e8efb, #a777e3); padding: 40px 0;'>"
                + "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; background: #fff; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);'>"
                + "<h2 style='color: #6e8efb; text-align: center;'>Подтверждение учетной записи</h2>"
                + "<p>Здравствуйте, <b>" + user.getUsername() + "</b>!</p>"
                + "<p>Данное письмо отправлено для подтверждения Вашей учетной записи в приложении <b>Запись клиентов</b>. Чтобы подтвердить учетную запись, "
                + "<a href='" + verificationLink + "' style='color: #6e8efb; text-decoration: none;'>перейдите по ссылке</a>.</p>"
                + "<p style='color: #f44336; font-weight: bold;'>Ссылка действительна в течение 24 часов.</p>"
                + "<hr style='border:none; border-top:1px solid #e0e0e0; margin: 20px 0;'/>"
                + "<p style='font-size: 14px;'>С уважением,<br>"
                + "<b>Команда приложения Запись клиентов</b></p>"
                + "<p style='font-size: 12px; color: #888;'>Пожалуйста, не отвечайте на это письмо. Для связи с нами используйте контактные данные на нашем сайте.</p>"
                + "</div>"
                + "</div>";

        try {
            emailService.sendEmailWithHtml(user.getUserEmail(), htmlSubject, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}