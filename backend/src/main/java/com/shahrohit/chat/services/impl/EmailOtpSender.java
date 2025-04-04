package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.services.EmailService;
import com.shahrohit.chat.services.OtpSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailOtpSender implements OtpSender {
    private final EmailService emailService;

    @Override
    public void sendOtp(String recipient, String otp) {
        emailService.sendOtpEmail(recipient, otp);
    }
}
