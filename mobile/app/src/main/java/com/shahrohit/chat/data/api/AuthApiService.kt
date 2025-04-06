package com.shahrohit.chat.data.api

import com.shahrohit.chat.data.dto.AuthResponse
import com.shahrohit.chat.data.dto.LoginRequest
import com.shahrohit.chat.data.dto.RegisterRequest
import com.shahrohit.chat.data.dto.UserAvailabilityResponse
import com.shahrohit.chat.data.dto.VerifyOtpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest) : AuthResponse

    @GET("auth/check-username")
    suspend fun checkUsernameAvailability(@Query("username") username: String): UserAvailabilityResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest) : AuthResponse

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest) : AuthResponse
}

