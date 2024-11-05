package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.controller.rest.dto.AuthenticationRequestDto;
import com.lemmiwinks.myscheduleserver.controller.rest.dto.RegistrationRequestDto;
import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.PasswordResetToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.PasswordResetTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.security.JwtTokenProvider;
import com.lemmiwinks.myscheduleserver.service.ConfirmationTokenService;
import com.lemmiwinks.myscheduleserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Value("${web.url}")
    private String webUrl;
    @Autowired
    private EmailService emailService; // Сервис для отправки email
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Этот метод принимает POST запрос на /login и аутентифицирует пользователя
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        try {
            // При передаче данных мы аутентифицируем пользователя на основании username и password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword()));

            // Получаем user
            User user;
            try {
                user = userRepository.findByUsername(authenticationRequestDto.getUsername());
            } catch (UsernameNotFoundException e) {
                throw new UsernameNotFoundException("User doesn't exists");
            }

            // Токен провайдер создает токен
            String token = jwtTokenProvider.createToken(authenticationRequestDto.getUsername(), user.getUserEmail());

            // Если все ок - передаем пользователю токен
            Map<Object, Object> response = new HashMap<>();
            response.put("username", authenticationRequestDto.getUsername());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    // Метод для выхода пользователя
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Создаем обработчик для завершения сессии и очистки контекста безопасности
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    // Метод для сброса пароля
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody RegistrationRequestDto registrationRequestDto) {
        Map<Object, Object> response = new HashMap<>();

        // Найти пользователя по login
        User user = userRepository.findByUsername(registrationRequestDto.getUsername());
        if (user == null) {
            // Найти пользователя по email, если по login не получилось
            user = userRepository.findByUserEmail(registrationRequestDto.getEmail());
        }

        if (user == null) {
            // Если пользователь не найден, вернуть ошибку
            response.put("status", "Пользователь не найден");
            return ResponseEntity.ok(response);
        }

        if (passwordResetTokenRepository.findByUserId(user.getId()) != null) {
            response.put("status", "Токен уже был отправлен на почту пользователя. Воспользуйтесь токеном или подождите 24 часа для запроса нового токена.");
            return ResponseEntity.ok(response);
        }

        // Генерация токена для сброса пароля
        generatePasswordResetToken(user);

        // Отправка email с инструкциями по сбросу пароля
        sendPasswordResetEmail(user);

        // Уведомление об успешной отправке письма
        response.put("status", "Успешно! Письмо для сброса пароля отправлено на почту пользователя");
        return ResponseEntity.ok(response);
    }

    // Метод для получения данных пользователя
    @PostMapping("/get-user-data")
    public ResponseEntity<?> getUserData(@RequestBody AuthenticationRequestDto authenticationRequestDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        User user = userRepository.findByUsername(authenticationRequestDto.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/resend-confirmation-email")
    public ResponseEntity<Map<Object, Object>> resendConfirmationEmail(@RequestBody User user) {
        Map<Object, Object> response = new HashMap<>();

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByUserId(user.getId());

        if (confirmationToken != null) {
            response.put("status", "С момента отправки предыдущего токена не прошло 24 часа.");
            return ResponseEntity.ok(response);
        } else {
            // Создаем и отправляем новый токен
            confirmationTokenService.saveToken(user);
            confirmationTokenService.sendVerificationEmail(user);

            response.put("status", "Письмо для подтверждения аккаунта отправлено на почту: " + user.getUserEmail());
            return ResponseEntity.ok(response);
        }
    }

    public void generatePasswordResetToken(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public void sendPasswordResetEmail(User user) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(user.getId());

        String verificationLink = webUrl + "/password-reset?token=" + passwordResetToken.getPasswordResetToken();

        String htmlSubject = "Приложение Запись клиентов - сброс пароля";

        // Оформленный HTML-контент письма
        String htmlContent = "<div style='background: linear-gradient(135deg, #6e8efb, #a777e3); padding: 40px 0;'>"
                + "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; background: #fff; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);'>"
                + "<h2 style='color: #6e8efb; text-align: center;'>Сброс пароля</h2>"
                + "<p>Здравствуйте, <b>" + user.getUsername() + "</b>!</p>"
                + "<p>Вы запросили сброс пароля для Вашей учетной записи в приложении <b>Запись клиентов</b>. Чтобы установить новый пароль, "
                + "<a href='" + verificationLink + "' style='color: #6e8efb; text-decoration: none;'>перейдите по ссылке</a>.</p>"
                + "<p style='color: #f44336; font-weight: bold;'>Ссылка действительна в течение 1 часа.</p>"
                + "<p>Если Вы не запрашивали сброс пароля, просто проигнорируйте это сообщение.</p>"
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