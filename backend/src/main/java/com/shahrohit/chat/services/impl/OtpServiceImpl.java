package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.models.OtpEntity;
import com.shahrohit.chat.enums.OtpType;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.repositories.OtpRepository;
import com.shahrohit.chat.services.OtpSender;
import com.shahrohit.chat.services.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.shahrohit.chat.utils.OtpGenerator;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpSender otpSender;
    private final OtpRepository otpRepository;

    @Override
    public void sendOtp(User user, OtpType type) {
        otpRepository.findByUser(user).ifPresent(otpRepository::delete);

        String otpCode = OtpGenerator.generateOtp();

        OtpEntity otpEntity = OtpEntity.builder()
            .otp(otpCode)
            .user(user)
            .type(type)
            .expiresAt(LocalDateTime.now().plusMinutes(5))
            .build();

        otpRepository.save(otpEntity);
        otpSender.sendOtp(user.getEmail(), otpCode);
    }

    @Override
    public boolean verifyOtp(User user, String otpCode, OtpType type) {
        return otpRepository.findByUser(user)
            .filter(otp -> otp.getType().equals(type) && otp.getOtp().equals(otpCode) && otp.getExpiresAt().isAfter(LocalDateTime.now()))
            .map(otp -> {
                otpRepository.delete(otp);
                user.setEnabled(true);
                return true;
            })
            .orElse(false);
    }

    @Scheduled(fixedRate = 600000) // Runs every 10 minutes (600,000 ms)
    private void cleanupExpiredOtp() {
        int deletedCount = otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        if (deletedCount > 0) {
            System.out.println("✅ Deleted " + deletedCount + " expired OTPs");
        }
    }
}
