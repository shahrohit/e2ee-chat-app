package com.shahrohit.chat.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenRequest {
    @NotBlank(message = "Refresh token is Required")
    private String refreshToken;

    @NotBlank(message = "Device is Required")
    private String deviceFingerprint;
}
