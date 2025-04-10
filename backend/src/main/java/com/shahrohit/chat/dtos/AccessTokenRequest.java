package com.shahrohit.chat.dtos;

import jakarta.validation.constraints.NotBlank;

public record AccessTokenRequest(
    @NotBlank(message = "Refresh token is Required")
    String refreshToken,

    @NotBlank(message = "Device is Required")
    String deviceFingerprint
){}
