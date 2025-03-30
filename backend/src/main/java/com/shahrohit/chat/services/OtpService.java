package com.shahrohit.chat.services;


import com.shahrohit.chat.models.User;

public interface OtpService {
    void sendOtp(User user);
    boolean verifyOtp(User user, String otp);
}
