package com.shahrohit.chat.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email or username is Required")
    private String identifier;

    @NotBlank(message = "Password is Required")
    private String password;

    @NotBlank(message = "Device is Required")
    private String deviceFingerprint;
}
