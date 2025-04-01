package com.shahrohit.chat.services;

import com.shahrohit.chat.dtos.LoginRequest;
import com.shahrohit.chat.dtos.AuthResponse;
import com.shahrohit.chat.dtos.RegisterRequest;
import com.shahrohit.chat.dtos.VerifyOtpRequest;


public interface AuthService {
    AuthResponse registerUser(RegisterRequest user);
    AuthResponse verifyOtp(VerifyOtpRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse verifyNewDevice(VerifyOtpRequest request);
}
