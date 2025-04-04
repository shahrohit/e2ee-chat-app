package com.shahrohit.chat.services;

public interface TokenService {
    String generateAccessToken(String username);
    String generateRefreshToken();
    String extractUsername(String token);
    boolean isTokenValid(String token, String username);
}
