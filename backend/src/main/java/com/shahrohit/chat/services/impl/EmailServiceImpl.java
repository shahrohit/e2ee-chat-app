package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendOtpEmail(String to, String otp) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Your One Time Password");
            helper.setText("Your OTP is: <b>" + otp + "</b>. It is valid for 5 minutes.", true);

            mailSender.send(message);

            System.out.println("Otp Send");

        }catch (MessagingException e){
            throw new RuntimeException("Failed to send Otp", e);
        }

    }
}
