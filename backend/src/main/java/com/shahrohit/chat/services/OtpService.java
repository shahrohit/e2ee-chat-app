package com.shahrohit.chat.services;

import com.shahrohit.chat.enums.OtpType;
import com.shahrohit.chat.models.User;

public interface OtpService {
    void sendOtp(User user, OtpType type);
    boolean verifyOtp(User user, String otp, OtpType type);
}
