package com.lemmiwinks.myscheduleserver.controller;

import com.lemmiwinks.myscheduleserver.entity.PasswordResetToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.PasswordResetTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.EmailService;
import com.lemmiwinks.myscheduleserver.service.UserService;
import com.lemmiwinks.myscheduleserver.service.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;

@Controller
public class PasswordResetController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserService userService; // Сервис для работы с пользователями
    @Autowired
    private EmailService emailService; // Сервис для отправки email
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // Показать страницу восстановления пароля
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    // Обработка формы восстановления пароля
    @PostMapping("/forgot-password")
    public String handleForgotPassword(
            @RequestParam("emailOrUsername") String emailOrUsername,
            Model model) {

        // Найти пользователя по email или имени
        User user = userRepository.findByUsername(emailOrUsername);

        if (user == null) {
            user = userRepository.findByUserEmail(emailOrUsername);
        }

        if (user == null) {
            // Если пользователь не найден, вернуть ошибку
            model.addAttribute("passwordError", "Пользователь не найден");
            return "forgot-password";
        }

        if (passwordResetTokenRepository.findByUserId(user.getId()) != null) {
            model.addAttribute("passwordError", "Токен уже был отправлен на почту пользователя");
            return "forgot-password";
        }

        // Генерация токена для сброса пароля
        generatePasswordResetToken(user);

        // Отправка email с инструкциями по сбросу пароля
        sendPasswordResetEmail(user);

        // Уведомление об успешной отправке письма
        model.addAttribute("successMessage", "Успешно! Токен был отправлен на почту пользователя");

        return "forgot-password";
    }

    public void generatePasswordResetToken(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public void sendPasswordResetEmail(User user) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(user.getId());

        String verificationLink = "http://localhost:8080/password-reset?token=" + passwordResetToken.getConfirmationToken();

        String htmlSubject = "Приложение Запись клиентов - сброс пароля";
        String htmlContent = "Добро пожаловать, <b>" + user.getUsername() + "</b>!"
                + "<br>Чтобы установить новый пароль от Вашей учетной записи, "
                + "<a href='" + verificationLink + "'>перейдите по ссылке</a>."
                + "<br>Ссылка действительна 1 час.<br><br>"
                + "<br>Проигнорируйте это письмо если не запрашивали сброс пароля.<br><br>"
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

    @GetMapping("/password-reset")
    public String showPasswordResetForm(@RequestParam("token") String passwordResetToken, Model model) {
        // Найти токен в базе данных
        PasswordResetToken resetToken = passwordResetTokenRepository.findByPasswordResetToken(passwordResetToken);

        // Проверить, существует ли токен и не истек ли его срок действия
        if (resetToken == null || Util.isTokenExpired(resetToken.getCreatedDate(), 1)) {
            model.addAttribute("message", "Ссылка для сброса пароля недействительна или устарела.");
            return "/message"; // Перенаправить на страницу сообщением
        }

        // Если токен действителен, показать форму для ввода нового пароля
        model.addAttribute("token", passwordResetToken);
        return "password-reset-form"; // JSP для формы ввода нового пароля
    }

    @PostMapping("/password-reset")
    public String handlePasswordReset(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("newPasswordConfirm") String newPasswordConfirm,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Найти токен в базе данных
        PasswordResetToken resetToken = passwordResetTokenRepository.findByPasswordResetToken(token);

        // Проверить, существует ли токен и не истек ли его срок действия
        if (resetToken == null || Util.isTokenExpired(resetToken.getCreatedDate(), 1)) {
            redirectAttributes.addFlashAttribute("message", "Ссылка для сброса пароля недействительна или устарела.");
            return "redirect:/password-reset?token=" + token;
        }

        // Проверка, что пароли совпадают
        if (!newPassword.equals(newPasswordConfirm)) {
            redirectAttributes.addFlashAttribute("message", "Пароли не совпадают.");
            return "redirect:/password-reset?token=" + token;
        }

        // Проверка, что пароль соответствует требованиям длины
        if (newPassword.length() < 8 || newPassword.length() > 16) {
            redirectAttributes.addFlashAttribute("message", "Пароль должен содержать от 8 до 16 символов.");
            return "redirect:/password-reset?token=" + token;
        }

        // Обновление пароля пользователя
        User user = resetToken.getUserEntity();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        userService.updateUser(user);

        // Удаление токена после успешного сброса пароля
        passwordResetTokenRepository.delete(resetToken);

        // Добавление уведомления об успешной смене пароля
        model.addAttribute("message", "Пароль успешно изменен");
        return "message";
    }
}