package com.lemmiwinks.myscheduleserver.service.impl;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.repository.ConfirmationTokenRepository;
import com.lemmiwinks.myscheduleserver.repository.UserRepository;
import com.lemmiwinks.myscheduleserver.service.EmailService;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

        String htmlSubject = "Приложение Запись клиентов - завершение регистрации";
        String htmlContent = "Добро пожаловать, <b>" + user.getUsername() + "</b>!" + "<br>Чтобы завершить регистрацию, " +
                "<a href='" + verificationLink + "'>перейдите по ссылке</a>." +
                "<br>Ссылка действительна 24 часа.<br>";

        try {
            emailService.sendEmailWithHtml(user.getUserEmail(),
                    htmlSubject, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByUserEmail(token.getUserEntity().getUserEmail());
            user.setEnabled(true);
            userRepository.save(user);
        }

        return token != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  Spring Security использует BCryptPasswordEncoder для проверки введённого пароля пользователя.
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
