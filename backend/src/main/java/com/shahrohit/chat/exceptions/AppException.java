package com.shahrohit.chat.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class AppException extends RuntimeException{
    protected int statusCode;
    protected String message;
    protected Map<String, Object> errorData;

}
