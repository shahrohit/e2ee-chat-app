package com.shahrohit.chat.dtos;

import jakarta.validation.constraints.NotBlank;

public record PublicKeyRequest(
    Long userId,

    @NotBlank(message = "Public Key is Required")
    String publicKey,

    @NotBlank(message = "Device is Required")
    String deviceFingerprint
){}
