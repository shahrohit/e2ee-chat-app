package com.shahrohit.chat.repositories;

import com.shahrohit.chat.models.Session;
import com.shahrohit.chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByUser(User user);
    Optional<Session> findByRefreshToken(String refreshToken);
}
