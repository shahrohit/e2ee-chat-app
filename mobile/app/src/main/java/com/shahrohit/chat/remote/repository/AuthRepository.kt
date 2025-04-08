package com.shahrohit.chat.data.repository

import com.shahrohit.chat.data.dto.AuthResponse
import com.shahrohit.chat.data.dto.LoginRequest
import com.shahrohit.chat.data.dto.RegisterRequest
import com.shahrohit.chat.data.dto.UserAvailabilityResponse
import com.shahrohit.chat.data.dto.VerifyOtpRequest

interface AuthRepository{
    suspend fun register(request: RegisterRequest) : Result<AuthResponse>
    suspend fun checkUsername(username: String) : Result<UserAvailabilityResponse>
    suspend fun login(request : LoginRequest) : Result<AuthResponse>
    suspend fun verifyOtp(request : VerifyOtpRequest) : Result<AuthResponse>
}