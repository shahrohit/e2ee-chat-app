package com.shahrohit.chat.dtos;

import com.shahrohit.chat.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    String email,

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{2,}$", message = "Invalid username (letters, numbers, underscores; must start with a letter)")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password
){
    public User toUser(){
        return User.builder()
            .name(name)
            .username(username)
            .email(email)
            .password(password)
            .enabled(false)
            .build();
    }
}
