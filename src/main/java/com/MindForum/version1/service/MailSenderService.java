package com.MindForum.version1.service;

public interface MailSenderService {
    void sendEmailVerificationCode(String email, String code);
}
