package com.shahrohit.chat.dtos;

import com.shahrohit.chat.enums.FriendStatus;

public record UserProfile(
    String name,
    String username,
    String profilePictureUrl,
    FriendStatus friendStatus
) {}
