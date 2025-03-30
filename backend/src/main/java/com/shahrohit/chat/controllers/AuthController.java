package com.shahrohit.chat.controllers;

import com.shahrohit.chat.dtos.RegisterRequest;
import com.shahrohit.chat.dtos.VerifyOtpRequest;
import com.shahrohit.chat.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterRequest body){
        return authService.registerUser(body);
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@Valid @RequestBody VerifyOtpRequest body){
        return authService.verifyOtp(body.getUsername(), body.getOtp());
    }
}
