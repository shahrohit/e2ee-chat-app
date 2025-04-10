package com.shahrohit.chat.dtos;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse(
    int statusCode,
    String message,
    Map<String, Object> errorData
){}
