package com.MindForum.version1.service.impl;

import com.MindForum.version1.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailVerificationCode(String email, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("MindForum");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verify code");
        simpleMailMessage.setText("This is the verification code used to recover your password: " + code + "\nPlease do not share it with anyone.");

        javaMailSender.send(simpleMailMessage);
    }
}
