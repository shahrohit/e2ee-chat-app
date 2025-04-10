package com.shahrohit.chat.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Email or username is Required")
    String identifier,

    @NotBlank(message = "Password is Required")
    String password,

    @NotBlank(message = "Device is Required")
    String deviceFingerprint
){}
