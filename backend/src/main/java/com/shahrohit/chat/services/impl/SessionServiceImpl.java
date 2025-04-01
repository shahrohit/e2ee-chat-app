package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.models.Session;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.repositories.SessionRepository;
import com.shahrohit.chat.services.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Optional<Session> getSession(User user) {
        return sessionRepository.findByUser(user);
    }

    @Override
    public void createSession(User user, String refreshToken, String deviceFingerprint) {
        sessionRepository.findByUser(user).ifPresent(sessionRepository::delete);

        Session session = Session.builder()
            .user(user)
            .refreshToken(refreshToken)
            .expiryDate(LocalDateTime.now().plusDays(30))
            .deviceFingerprint(deviceFingerprint)
            .build();

        sessionRepository.save(session);
    }
}
