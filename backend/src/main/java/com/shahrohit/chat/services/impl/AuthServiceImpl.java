package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.adapters.UserAdapter;
import com.shahrohit.chat.dtos.RegisterRequest;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.repositories.UserRepository;
import com.shahrohit.chat.services.AuthService;
import com.shahrohit.chat.services.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserAdapter userAdapter;
    private final OtpService otpService;
    

    @Override
    public String registerUser(RegisterRequest body) {
        if(userRepository.findByEmail(body.getEmail()).isPresent()){
            return "Email is Already Taken";
        }

        if(userRepository.findByUsername(body.getUsername()).isPresent()){
            return "Username is Already Taken";
        }

        User newUser = userAdapter.toUser(body);
        userRepository.save(newUser);

        otpService.sendOtp(newUser);

        return "User Register Successfully. Please Verify the otp sent to your register email address";
    }

    @Override
    public String verifyOtp(String username, String otp) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            return "Invalid User";
        }

        User user = userOptional.get();

        if(user.isEnabled()){
            return "User Already Verified";
        }

        boolean isVerified = otpService.verifyOtp(user,otp);

        if(!isVerified){
            return "Invalid User or OTP";
        }

        user.setEnabled(true);
        userRepository.save(user);

        return "User Verified Successfully";
    }
}
