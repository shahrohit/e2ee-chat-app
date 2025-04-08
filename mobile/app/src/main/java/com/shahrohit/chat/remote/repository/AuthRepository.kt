package com.shahrohit.chat.remote.repository

import com.shahrohit.chat.enums.OtpFor
import com.shahrohit.chat.remote.dto.AccessTokenRequest
import com.shahrohit.chat.remote.dto.AuthResponse
import com.shahrohit.chat.remote.dto.LoginRequest
import com.shahrohit.chat.remote.dto.RegisterRequest
import com.shahrohit.chat.remote.dto.UserAvailabilityResponse
import com.shahrohit.chat.remote.dto.VerifyOtpRequest

interface AuthRepository{
    suspend fun register(request: RegisterRequest) : Result<AuthResponse>
    suspend fun checkUsername(username: String) : Result<UserAvailabilityResponse>
    suspend fun login(request : LoginRequest) : Result<AuthResponse>
    suspend fun verifyOtp(request : VerifyOtpRequest, otpFor : OtpFor) : Result<AuthResponse>
    suspend fun refreshAccessToken(request: AccessTokenRequest) : Result<AuthResponse>
}