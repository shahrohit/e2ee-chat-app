package com.shahrohit.chat.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {
    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "OTP is required")
    @Size(min = 6, max = 6, message = "Invalid OTP")
    String otp;

    @NotBlank(message = "Device is Required")
    private String deviceFingerprint;
}
