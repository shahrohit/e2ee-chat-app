package com.shahrohit.chat.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicKeyRequest {
    @NotBlank(message = "Username is Required")
    private String username;

    @NotBlank(message = "Public Key is Required")
    private String publicKey;

    @NotBlank(message = "Device is Required")
    private String deviceFingerprint;
}
