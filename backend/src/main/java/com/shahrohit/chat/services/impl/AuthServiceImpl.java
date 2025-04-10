package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.dtos.*;
import com.shahrohit.chat.enums.AuthStatus;
import com.shahrohit.chat.enums.OtpType;
import com.shahrohit.chat.exceptions.Conflict;
import com.shahrohit.chat.exceptions.Forbidden;
import com.shahrohit.chat.exceptions.NotFound;
import com.shahrohit.chat.models.Session;
import com.shahrohit.chat.models.User;
import com.shahrohit.chat.repositories.UserRepository;
import com.shahrohit.chat.services.AuthService;
import com.shahrohit.chat.services.OtpService;
import com.shahrohit.chat.services.SessionService;
import com.shahrohit.chat.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final TokenService tokenService;
    private final SessionService sessionService;
    private final PasswordEncoder passwordEncoder;

    private AuthResponse getAuthResponse(User user, String deviceFingerprint) {
        String accessToken = tokenService.generateAccessToken(user.getUsername());
        String refreshToken = tokenService.generateRefreshToken();

        sessionService.createSession(user, refreshToken, deviceFingerprint);

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(UserDto.fromUser(user))
            .status(AuthStatus.USER_VERIFIED.toString())
            .message("Login Successfully")
            .build();
    }


    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new Conflict("Email is Already Taken");
        }

        if(userRepository.existsByUsername(request.username())){
            throw new Conflict("Username is Already Taken");
        }

        User newUser = request.toUser();
        newUser.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(newUser);

        otpService.sendOtp(newUser, OtpType.NEW_ACCOUNT_VERIFICATION);

        return AuthResponse.builder()
            .accessToken(null)
            .refreshToken(null)
            .user(UserDto.fromUser(newUser))
            .status(AuthStatus.USER_VERIFICATION_PENDING.toString())
            .message("User Register Successfully")
            .build();
    }

    @Override
    public AuthResponse verifyOtp(VerifyOtpRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.username());
        if(userOptional.isEmpty()){
            throw new NotFound("User Not Found");
        }

        User user = userOptional.get();

        if(user.isEnabled()){
            throw new Forbidden("User Already Verified");
        }

        boolean isVerified = otpService.verifyOtp(user,request.otp(), OtpType.NEW_ACCOUNT_VERIFICATION);

        if(!isVerified){
            throw new Forbidden("Invalid User or OTP");
        }

        user.setEnabled(true);
        userRepository.save(user);

        return getAuthResponse(user, request.deviceFingerprint());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailOrUsername(request.identifier(), request.identifier())
            .orElseThrow(() -> new Forbidden("Invalid Credentials"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new Forbidden("Invalid Credentials");
        }

        if(!user.isEnabled()){
            otpService.sendOtp(user, OtpType.NEW_ACCOUNT_VERIFICATION);
            return AuthResponse.builder()
                .accessToken(null)
                .refreshToken(null)
                .user(UserDto.fromUser(user))
                .status(AuthStatus.USER_VERIFICATION_PENDING.toString())
                .message("An OTP is sent to : " + user.getEmail())
                .build();
        }

        Optional<Session> existingSession = sessionService.getSession(user);

        if(existingSession.isPresent()){
            Session currentSession = existingSession.get();
            if(!currentSession.getDeviceFingerprint().equals(request.deviceFingerprint())){
                otpService.sendOtp(user, OtpType.NEW_DEVICE_VERIFICATION);
                return AuthResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .user(UserDto.fromUser(user))
                    .status(AuthStatus.DEVICE_VERIFICATION_PENDING.toString())
                    .message("An OTP is send to : " + user.getEmail())
                    .build();
            }
        }

        return getAuthResponse(user, request.deviceFingerprint());
    }

    @Override
    public AuthResponse verifyNewDevice(VerifyOtpRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.username());
        if(userOptional.isEmpty()){
            throw new NotFound("User Not Found");
        }

        User user = userOptional.get();

        if(!user.isEnabled()){
            throw new RuntimeException("User Not Verified");
        }

        boolean isVerified = otpService.verifyOtp(user, request.otp(), OtpType.NEW_DEVICE_VERIFICATION);

        if(!isVerified){
            throw new RuntimeException("Invalid OTP");
        }

        String accessToken = tokenService.generateAccessToken(user.getUsername());
        String refreshToken = tokenService.generateRefreshToken();

        sessionService.createSession(user, refreshToken, request.deviceFingerprint());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(UserDto.fromUser(user))
            .status(AuthStatus.DEVICE_VERIFIED.toString())
            .message("User verified")
            .build();
    }

    @Override
    public boolean checkUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public AuthResponse refreshAccessToken(AccessTokenRequest request) {
       Session session = sessionService.getSessionFromRefreshToken(request.refreshToken())
           .orElseThrow(() -> new RuntimeException("Unauthorized: Invalid Token"));

       if(!session.getDeviceFingerprint().equals(request.deviceFingerprint())){
           throw new RuntimeException("Unauthorized: Invalid Request");
       }

       User user = session.getUser();
       String accessToken = tokenService.generateAccessToken(user.getUsername());
       String refreshToken = tokenService.generateRefreshToken();

       sessionService.createSession(user, refreshToken, request.deviceFingerprint());


        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(UserDto.fromUser(user))
            .status(AuthStatus.USER_VERIFIED.toString())
            .message("User Verified")
            .build();
    }
}
