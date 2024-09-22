package com.lemmiwinks.myscheduleserver.controller;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class AccountController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    ConfirmationTokenService confirmationTokeService;

    @GetMapping("/account")
    public String account(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        inflateAccountModel(model, user);

        return "account";
    }

    @PostMapping("/resend-confirmation-email")
    public String resendConfirmationEmail(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByUserId(user.getId());

        if (!isTokenExpired(confirmationToken.getConfirmationToken())) {
            model.addAttribute("resendTokenStatusMsg", "С момента отправки предыдущего токена не прошло 24 часа.");
            model.addAttribute("resendTokenStatusMsgColor", "red");
        } else {
            // Генерируем новый токен и отправляем пользователю на почту
            // РЕАЛИЗОВАТЬ + КАПЧУ ПРИ РЕГИСТРАЦИИ + РЕАЛИЗАЦИЯ "ЗАБЫЛ ПАРОЛЬ"
            confirmationTokeService.updateToken(confirmationToken);
            confirmationTokeService.sendVerificationEmail(user);

            model.addAttribute("resendTokenStatusMsg", "Новый токен отправлен на почту " + user.getUserEmail());
            model.addAttribute("resendTokenStatusMsgColor", "green");
        }

        // Повторно заполняем модель для корректного отображения страницы аккаунта
        inflateAccountModel(model, user);

        return "account";
    }

    private void inflateAccountModel(Model model, User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userEmail", user.getUserEmail());

        if (user.getEmailVerified()) {
            model.addAttribute("isVerified", "Аккаунт подтвержден");
            model.addAttribute("verificationColor", "green");
            model.addAttribute("showResendButton", false);
        } else {
            model.addAttribute("isVerified", "Аккаунт не подтвержден! Перейдите по ссылке в письме, чтобы подтвердить аккаунт.");
            model.addAttribute("verificationColor", "red");
            model.addAttribute("showResendButton", true);
        }
    }

    public boolean isTokenExpired(String confirmationToken) {
        // Получаем дату создания токена
        Date tokenCreatedDate = confirmationTokenRepository.findByConfirmationToken(confirmationToken).getCreatedDate();
        LocalDateTime tokenCreatedDateTime = convertToLocalDateTime(tokenCreatedDate);

        // Получаем текущее время
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Вычисляем разницу во времени между текущим временем и датой создания токена
        Duration duration = Duration.between(tokenCreatedDateTime, currentDateTime);

        // Проверяем, прошло ли больше 24 часов
        return duration.toHours() > 24;
    }

    public LocalDateTime convertToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
