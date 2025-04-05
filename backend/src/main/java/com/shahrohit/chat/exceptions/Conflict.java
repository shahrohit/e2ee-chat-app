package com.shahrohit.chat.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class Conflict extends AppException{
    public Conflict(String message, Map<String, Object> errorData) {
        super(HttpStatus.CONFLICT.value(),message, errorData);
    }
    public Conflict(String message) {
        super(HttpStatus.CONFLICT.value(), message, null);
    }
}
