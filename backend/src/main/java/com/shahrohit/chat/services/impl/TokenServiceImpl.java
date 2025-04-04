package com.shahrohit.chat.services.impl;

import com.shahrohit.chat.services.TokenService;
import com.shahrohit.chat.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtUtil jwtUtil;

    public String generateAccessToken(String username){
        return jwtUtil.generateAccessToken(username);
    }

    public String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }

    public String extractUsername(String token){
        return jwtUtil.extractSubject(token);
    }

    public boolean isTokenValid(String token, String username){
        return jwtUtil.isTokenValid(token,username);
    }

}
