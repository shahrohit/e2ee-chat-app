package com.shahrohit.chat.remote.repository

import com.shahrohit.chat.remote.dto.PingResponse
import com.shahrohit.chat.remote.dto.PublicKeyRequest
import com.shahrohit.chat.remote.dto.UploadKeyResponse
import com.shahrohit.chat.remote.dto.UserProfile

interface UserRepository {
    suspend fun uploadPublicKey(request : PublicKeyRequest) : Result<UploadKeyResponse>
    suspend fun pingUser() : Result<PingResponse>
    suspend fun searchUser(query : String) : Result<List<UserProfile>>
    suspend fun sendFriendRequest(username : String) : Result<Boolean>
    suspend fun getReceivedFriendRequests() : Result<List<UserProfile>>
    suspend fun getSentFriendRequests() : Result<List<UserProfile>>
    suspend fun responseToFriendRequest(username : String, accepted : Boolean) : Result<Boolean>
    suspend fun cancelFriendRequest(username : String) : Result<Boolean>
    suspend fun getFriends() : Result<List<UserProfile>>
}