package com.shahrohit.chat.remote.api

import com.shahrohit.chat.remote.dto.AccessTokenRequest
import com.shahrohit.chat.remote.dto.AuthResponse
import com.shahrohit.chat.remote.dto.LoginRequest
import com.shahrohit.chat.remote.dto.RegisterRequest
import com.shahrohit.chat.remote.dto.UserAvailabilityResponse
import com.shahrohit.chat.remote.dto.VerifyOtpRequest
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
    suspend fun verifyUser(@Body request: VerifyOtpRequest) : AuthResponse

    @POST("auth/verify-device")
    suspend fun verifyDevice(@Body request: VerifyOtpRequest) : AuthResponse

    @POST("auth/refresh-token")
    suspend fun refreshAccessToken(@Body request: AccessTokenRequest) : AuthResponse
}

