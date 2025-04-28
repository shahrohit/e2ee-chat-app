package com.shahrohit.chat.remote.api

import com.shahrohit.chat.remote.dto.PingResponse
import com.shahrohit.chat.remote.dto.PublicKeyRequest
import com.shahrohit.chat.remote.dto.UploadKeyResponse
import com.shahrohit.chat.remote.dto.UserProfile
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @POST("users/keys")
    suspend fun uploadKey(@Body request: PublicKeyRequest) : UploadKeyResponse

    @POST("users/ping")
    suspend fun pingUser() : PingResponse

    @GET("users/search/{query}")
    suspend fun searchUsers(@Path("query") query : String) : List<UserProfile>

    @POST("friends/request")
    suspend fun sendFriendRequest(@Query("username") username : String) : Boolean

    @GET("friends/request/received")
    suspend fun getReceivedFriendRequests() : List<UserProfile>

    @GET("friends/request/sent")
    suspend fun getSentFriendRequests() : List<UserProfile>

    @POST("friends/request/respond/{username}")
    suspend fun respondToFriendRequest(@Path("username") username : String, @Query("accepted") accepted : Boolean) : Boolean

    @DELETE("friends/request/cancel/{username}")
    suspend fun cancelFriendRequest(@Path("username") username : String) : Boolean

    @GET("friends/all")
    suspend fun getFriends() : List<UserProfile>
}