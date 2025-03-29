package com.shahrohit.chat.adapters;

import com.shahrohit.chat.dtos.RegisterRequest;
import com.shahrohit.chat.models.Role;
import com.shahrohit.chat.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {
    private final PasswordEncoder passwordEncoder;

    private UserAdapter(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(RegisterRequest request){
        return User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .about(request.getAbout())
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .enabled(false)
            .build();
    }
}
