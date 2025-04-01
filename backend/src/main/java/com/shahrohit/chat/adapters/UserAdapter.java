package com.shahrohit.chat.adapters;

import com.shahrohit.chat.dtos.RegisterRequest;
import com.shahrohit.chat.dtos.UserDto;
import com.shahrohit.chat.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {

    public User toUser(RegisterRequest request){
        return User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .about(request.getAbout())
            .username(request.getUsername())
            .email(request.getEmail())
            .password(request.getPassword())
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
