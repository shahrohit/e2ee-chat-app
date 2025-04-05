package com.shahrohit.chat.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class Forbidden extends AppException{
    public Forbidden(String message, Map<String, Object> errorData) {
        super(HttpStatus.FORBIDDEN.value(), message, errorData);
    }
    public Forbidden(String message) {
        super(HttpStatus.FORBIDDEN.value(), message, null);
    }
}
