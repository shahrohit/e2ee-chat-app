package com.shahrohit.chat.dtos;

import com.shahrohit.chat.models.User;
import lombok.Builder;

@Builder
public record UserDto (
    Long userId,
    String name,
    String about,
    String email,
    String username,
    String profilePictureUrl
){
    public static UserDto fromUser(User user) {
        return UserDto.builder()
            .userId(user.getId())
            .name(user.getName())
            .about(user.getAbout())
            .email(user.getEmail())
            .username(user.getUsername())
            .profilePictureUrl(user.getProfilePictureUrl())
            .build();
    }
}
