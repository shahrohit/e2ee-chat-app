package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.services.OtpSender;
import org.springframework.stereotype.Service;

@Service
public class EmailOtpSender implements OtpSender {
    @Override
    public void sendOtp(String recipient, String otp) {
        System.out.println("Sending OTP: " + otp + " to Email : " + recipient);
    }
}
