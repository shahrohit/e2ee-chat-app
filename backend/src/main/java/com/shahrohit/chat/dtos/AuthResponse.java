package com.shahrohit.chat.dtos;

import lombok.Builder;

@Builder
public record AuthResponse(
    String accessToken,
    String refreshToken,
    UserDto user,
    String status,
    String message
){}
