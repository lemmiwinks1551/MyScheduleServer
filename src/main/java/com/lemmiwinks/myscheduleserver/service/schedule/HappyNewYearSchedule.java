package com.lemmiwinks.myscheduleserver.service.schedule;

import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import java.util.List;

@Configuration
@EnableScheduling
public class HappyNewYearSchedule {

    // Поздравление с новым годом 31.12 в 9:00 по Мск всем зарегистрированным пользователям по email

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Scheduled(cron = "0 0 9 31 12 ?", zone = "Europe/Moscow")
    private void sendHappyNewYearLetter() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            String htmlSubject = "Приложение Запись клиентов - С Новым Годом!";

            String htmlContent = "<div style='background: linear-gradient(135deg, #6e8efb, #a777e3); padding: 40px 0;'>"
                    + "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; background: #fff; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);'>"
                    + "<h2 style='color: #6e8efb; text-align: center;'>С Новым Годом!</h2>"
                    + "<p>Уважаемый(ая) <b>" + user.getUsername() + "</b>,</p>"
                    + "<p>Команда приложения <b>Запись клиентов</b> искренне поздравляет вас с Новым годом!</p>"
                    + "<p>Желаем вам крепкого здоровья, счастья, успехов и благополучия в новом году. Пусть он принесёт много радости, вдохновения и новых достижений!</p>"
                    + "<p>Мы благодарны, что вы являетесь частью нашего сообщества и надеемся, что наше приложение помогает вам в достижении ваших целей.</p>"
                    + "<hr style='border:none; border-top:1px solid #e0e0e0; margin: 20px 0;'/>"
                    + "<p style='font-size: 14px;'>С наилучшими пожеланиями,<br>"
                    + "<b>Команда разработчиков</b></p>"
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
}
