package com.shahrohit.chat.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UserDto user;
    private String message;
}
