package com.shahrohit.chat.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String name;
    private String about;
    private String email;
    private String username;
    private String profilePictureUrl;
}
