package com.shahrohit.chat.remote.repository

import com.shahrohit.chat.remote.dto.PingResponse
import com.shahrohit.chat.remote.dto.PublicKeyRequest
import com.shahrohit.chat.remote.dto.UploadKeyResponse

interface UserRepository {
    suspend fun uploadPublicKey(request : PublicKeyRequest) : Result<UploadKeyResponse>
    suspend fun pingUser() : Result<PingResponse>
}