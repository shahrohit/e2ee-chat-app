package com.shahrohit.chat.services;

public interface OtpSender {
    void sendOtp(String recipient, String otp);
}
