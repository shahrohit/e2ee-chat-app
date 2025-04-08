package com.shahrohit.chat.remote.api

import com.shahrohit.chat.remote.dto.PingResponse
import com.shahrohit.chat.remote.dto.PublicKeyRequest
import com.shahrohit.chat.remote.dto.UploadKeyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @POST("users/keys")
    suspend fun uploadKey(@Body request: PublicKeyRequest) : UploadKeyResponse

    @POST("users/ping")
    suspend fun pingUser() : PingResponse
}