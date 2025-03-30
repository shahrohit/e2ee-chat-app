package com.shahrohit.chat.repositories;

import com.shahrohit.chat.models.OtpEntity;
import com.shahrohit.chat.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByUser(User user);

    @Transactional
    int deleteByExpiresAtBefore(LocalDateTime time);
}
