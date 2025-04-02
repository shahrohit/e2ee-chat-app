package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.adapters.UserAdapter;
import com.shahrohit.chat.dtos.LoginRequest;
import com.shahrohit.chat.dtos.AuthResponse;
import com.shahrohit.chat.dtos.RegisterRequest;
import com.shahrohit.chat.dtos.VerifyOtpRequest;
import com.shahrohit.chat.enums.AuthStatus;
import com.shahrohit.chat.enums.OtpType;
import com.shahrohit.chat.models.Session;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.repositories.UserRepository;
import com.shahrohit.chat.services.AuthService;
import com.shahrohit.chat.services.OtpService;
import com.shahrohit.chat.services.SessionService;
import com.shahrohit.chat.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserAdapter userAdapter;
    private final OtpService otpService;
    private final TokenService tokenService;
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse registerUser(RegisterRequest body) {

        if(userRepository.existsByEmail(body.getEmail())){
            throw new RuntimeException("Email is Already Taken");
        }

        if(userRepository.existsByUsername(body.getUsername())){
            throw new RuntimeException("Username is Already Taken");
        }

        User newUser = userAdapter.toUser(body);
        userRepository.save(newUser);

        otpService.sendOtp(newUser, OtpType.NEW_ACCOUNT_VERIFICATION);

        return AuthResponse.builder()
            .accessToken(null)
            .refreshToken(null)
            .user(userAdapter.toUserDto(newUser))
            .message(AuthStatus.USER_VERIFICATION_PENDING.toString())
            .build();
    }

    @Override
    public AuthResponse verifyOtp(VerifyOtpRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if(userOptional.isEmpty()){
            throw new RuntimeException("User Not Found");
        }

        User user = userOptional.get();

        if(user.isEnabled()){
            throw new RuntimeException("User Already Verified");
        }

        boolean isVerified = otpService.verifyOtp(user,request.getOtp(), OtpType.NEW_ACCOUNT_VERIFICATION);

        if(!isVerified){
            throw new RuntimeException("Invalid User or OTP");
        }

        user.setEnabled(true);
        userRepository.save(user);

        String accessToken = tokenService.generateAccessToken(user.getUsername());
        String refreshToken = tokenService.generateRefreshToken();

        sessionService.createSession(user, refreshToken,request.getDeviceFingerprint());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(userAdapter.toUserDto(user))
            .message(AuthStatus.USER_VERIFIED.toString())
            .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword())
        );

        if(!auth.isAuthenticated()){
            throw new RuntimeException("Invalid Credentials");
        }

        User user = (User) auth.getPrincipal();

        if(!user.isEnabled()){
            otpService.sendOtp(user, OtpType.NEW_ACCOUNT_VERIFICATION);
            return AuthResponse.builder()
                .accessToken(null)
                .refreshToken(null)
                .user(userAdapter.toUserDto(user))
                .message(AuthStatus.USER_VERIFICATION_PENDING.toString())
                .build();
        }

        Optional<Session> existingSession = sessionService.getSession(user);

        if(existingSession.isPresent()){
            Session currentSession = existingSession.get();
            if(!currentSession.getDeviceFingerprint().equals(request.getDeviceFingerprint())){
                otpService.sendOtp(user, OtpType.NEW_DEVICE_VERIFICATION);
                return AuthResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .user(userAdapter.toUserDto(user))
                    .message(AuthStatus.DEVICE_VERIFICATION_PENDING.toString())
                    .build();
            }
        }

        String accessToken = tokenService.generateAccessToken(user.getUsername());
        String refreshToken = tokenService.generateRefreshToken();

        sessionService.createSession(user, refreshToken, request.getDeviceFingerprint());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(userAdapter.toUserDto(user))
            .message(AuthStatus.USER_VERIFIED.toString())
            .build();
    }

    @Override
    public AuthResponse verifyNewDevice(VerifyOtpRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if(userOptional.isEmpty()){
            throw new RuntimeException("User Not Found");
        }

        User user = userOptional.get();

        if(!user.isEnabled()){
            throw new RuntimeException("User Not Verified");
        }

        boolean isVerified = otpService.verifyOtp(user, request.getOtp(), OtpType.NEW_DEVICE_VERIFICATION);

        if(!isVerified){
            throw new RuntimeException("Invalid OTP");
        }

        String accessToken = tokenService.generateAccessToken(user.getUsername());
        String refreshToken = tokenService.generateRefreshToken();

        sessionService.createSession(user, refreshToken, request.getDeviceFingerprint());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(userAdapter.toUserDto(user))
            .message(AuthStatus.DEVICE_VERIFIED.toString())
            .build();
    }
}
