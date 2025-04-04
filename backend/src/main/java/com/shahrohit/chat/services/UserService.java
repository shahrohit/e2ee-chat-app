package com.shahrohit.chat.services;

import com.shahrohit.chat.dtos.PublicKeyRequest;
import com.shahrohit.chat.models.User;

public interface UserService {

    boolean uploadPublicKey(PublicKeyRequest request, User user);
}
