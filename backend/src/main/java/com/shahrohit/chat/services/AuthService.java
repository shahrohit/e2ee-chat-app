package com.shahrohit.chat.services;

import com.shahrohit.chat.dtos.*;


public interface AuthService {
    AuthResponse registerUser(RegisterRequest user);
    AuthResponse verifyOtp(VerifyOtpRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse verifyNewDevice(VerifyOtpRequest request);
    AuthResponse refreshAccessToken(AccessTokenRequest request);
    boolean checkUsernameAvailable(String username);
}
