package com.shahrohit.chat.adapters;

import com.shahrohit.chat.dtos.RegisterRequest;
import com.shahrohit.chat.dtos.UserDto;
import com.shahrohit.chat.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserAdapter {

    private final PasswordEncoder passwordEncoder;

    public User toUser(RegisterRequest request){
        return User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .about(request.getAbout())
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .enabled(false)
            .build();
    }

    public UserDto toUserDto(User user){
        return UserDto.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .about(user.getAbout())
            .email(user.getEmail())
            .username(user.getUsername())
            .profilePictureUrl(user.getProfilePictureUrl())
            .build();
    }
}
