package com.lemmiwinks.myscheduleserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("emailService")
public class EmailService {

    @Value("${spring.mail.username}")
    private String springMailUsername;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithHtml(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        mimeMessage.setContent(htmlBody, "text/html; charset=UTF-8");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setFrom(springMailUsername);

        javaMailSender.send(mimeMessage);
    }
}
