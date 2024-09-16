package com.lemmiwinks.myscheduleserver.service.impl;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.EmailService;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void saveUser(User user) {
        // Хеширование пароля перед сохранением пользователя
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        String verificationLink = "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken();

        String htmlSubject = "Запись клиентов - завершение регистрации";
        String htmlContent = "Чтобы завершить регистрацию, " +
                "<a href='" + verificationLink + "'>перейдите по ссылке</a>.<br> " +
                "Ссылка действительна 24 часа. <br>";

        try {
            emailService.sendEmailWithHtml(user.getUserEmail(),
                    htmlSubject, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity.ok("Ссылка для верификации аккаунта отправлена на почтовый ящику, указанный при регистрации");
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findUserByUserEmail(token.getUserEntity().getUserEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Аккаунт подтвержден!");
        }
        return ResponseEntity.badRequest().body("Не удалось подтвердить аккаунт");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  Spring Security использует BCryptPasswordEncoder для проверки введённого пароля пользователя.
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
