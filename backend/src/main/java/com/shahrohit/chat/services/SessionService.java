package com.shahrohit.chat.services;

import com.shahrohit.chat.models.Session;
import com.shahrohit.chat.models.User;

import java.util.Optional;

public interface SessionService {
    Optional<Session> getSession(User user);
    void createSession(User user, String refreshToken, String deviceFingerprint);
    Optional<Session> getSessionFromRefreshToken(String refreshToken);
}
