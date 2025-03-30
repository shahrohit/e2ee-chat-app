package com.shahrohit.chat.services;

import com.shahrohit.chat.dtos.RegisterRequest;


public interface AuthService {
    String registerUser(RegisterRequest user);
    String verifyOtp(String username, String otp);
}
