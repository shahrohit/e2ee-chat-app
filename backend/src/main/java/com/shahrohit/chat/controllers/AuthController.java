package com.shahrohit.chat.controllers;

import com.shahrohit.chat.dtos.*;
import com.shahrohit.chat.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse registerUser(@Valid @RequestBody RegisterRequest body){
        return authService.registerUser(body);
    }

    @PostMapping("/verify-otp")
    public AuthResponse verifyOtp(@Valid @RequestBody VerifyOtpRequest body){
        return authService.verifyOtp(body);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest body){
        return authService.login(body);
    }

    @PostMapping("/verify-device")
    public AuthResponse verifyDevice(@Valid @RequestBody VerifyOtpRequest body){
        return authService.verifyNewDevice(body);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshAccessToken(@Valid @RequestBody AccessTokenRequest body){
        return authService.refreshAccessToken(body);
    }
}
