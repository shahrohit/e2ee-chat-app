package com.shahrohit.chat.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class NotFound extends AppException{
    public NotFound(String message, Map<String, Object> errorData) {
        super(HttpStatus.NOT_FOUND.value(), message, errorData);
    }
    public NotFound(String message) {
        super(HttpStatus.NOT_FOUND.value(), message, null);
    }
}
