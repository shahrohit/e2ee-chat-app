package com.shahrohit.chat.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Map<String, Object> errorData;
}
