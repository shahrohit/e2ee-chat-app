package com.shahrohit.chat.controllers;

import com.shahrohit.chat.dtos.*;
import com.shahrohit.chat.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest body){
        AuthResponse authResponse = authService.registerUser(body);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest body){
        AuthResponse authResponse = authService.verifyOtp(body);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest body){
        AuthResponse loginResponse = authService.login(body);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify-device")
    public AuthResponse verifyDevice(@Valid @RequestBody VerifyOtpRequest body){
        return authService.verifyNewDevice(body);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshAccessToken(@Valid @RequestBody AccessTokenRequest body){
        return authService.refreshAccessToken(body);
    }

    @GetMapping("/check-username")
    public ResponseEntity<UserAvailabilityResponse> checkUsername(@RequestParam String username){

        boolean isAvailable = false;
        String message = "Invalid Username";

        if(username != null){
           isAvailable = authService.checkUsernameAvailable(username);
           message = isAvailable ? null : "Username Already Taken";
        }

        UserAvailabilityResponse response = UserAvailabilityResponse.builder()
            .available(isAvailable)
            .message(message)
            .build();

        return ResponseEntity.ok(response);
    }
}
