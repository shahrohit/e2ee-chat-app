package com.shahrohit.chat.services;

import com.shahrohit.chat.dtos.PublicKeyRequest;
import com.shahrohit.chat.dtos.UserProfile;
import com.shahrohit.chat.models.User;

import java.util.List;

public interface UserService {

    boolean uploadPublicKey(PublicKeyRequest request, User user);
    List<UserProfile> searchUsers(String query, Long userId);
}
