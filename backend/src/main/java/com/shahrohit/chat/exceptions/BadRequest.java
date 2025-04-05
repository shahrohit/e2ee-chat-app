package com.shahrohit.chat.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequest extends AppException{
    public BadRequest(String message, Map<String, Object> errorData) {
        super(HttpStatus.BAD_REQUEST.value(), message, errorData);
    }

    public BadRequest(String message){
        super(HttpStatus.BAD_REQUEST.value(), message, null);
    }
}
