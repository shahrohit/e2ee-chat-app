package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.models.OtpEntity;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.repositories.OtpRepository;
import com.shahrohit.chat.services.OtpSender;
import com.shahrohit.chat.services.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import utils.OtpGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpSender otpSender;
    private final OtpRepository otpRepository;

    @Override
    public void sendOtp(User user) {

        Optional<OtpEntity> existingOtp = otpRepository.findByUser(user);
        if(existingOtp.isPresent()){
            OtpEntity otpEntity = existingOtp.get();
            if(otpEntity.getExpiresAt().isAfter(LocalDateTime.now())){
               // TODO: Implement Custom Exception
                throw new IllegalStateException("Otp already Send. Please Wait till it expires");
            } else{
                otpRepository.delete(otpEntity);
            }
        }

        String otp = OtpGenerator.generateOtp();

        OtpEntity otpEntity = OtpEntity.builder()
            .otp(otp)
            .user(user)
            .expiresAt(LocalDateTime.now().plusMinutes(5))
            .build();

        otpRepository.save(otpEntity);
        otpSender.sendOtp(user.getEmail(), otp);
    }

    @Override
    public boolean verifyOtp(User user, String otp) {
        // TODO: Implement Custom Exception
        OtpEntity otpEntity = otpRepository.findByUser(user)
            .orElseThrow(() -> new IllegalStateException("No OTP Found"));

        if(!otpEntity.getOtp().equals(otp)){
            // TODO: Implement Custom Exception
            throw new IllegalArgumentException("Invalid OTP");
        }

        if(otpEntity.getExpiresAt().isBefore(LocalDateTime.now())){
            otpRepository.delete(otpEntity);
            // TODO: Implement Custom Exception
            throw new IllegalArgumentException("OTP has expired. Request a new one.");
        }

        otpRepository.delete(otpEntity);
        return true;
    }

    @Scheduled(fixedRate = 600000) // Runs every 10 minutes (600,000 ms)
    private void cleanupExpiredOtp() {
        int deletedCount = otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        if (deletedCount > 0) {
            System.out.println("✅ Deleted " + deletedCount + " expired OTPs");
        }
    }
}
