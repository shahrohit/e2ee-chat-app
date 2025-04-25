package com.shahrohit.chat.remote.repository.impl

import androidx.room.util.query
import com.shahrohit.chat.remote.api.UserApiService
import com.shahrohit.chat.remote.dto.PingResponse
import com.shahrohit.chat.remote.dto.PublicKeyRequest
import com.shahrohit.chat.remote.dto.UploadKeyResponse
import com.shahrohit.chat.remote.dto.UserProfile
import com.shahrohit.chat.remote.repository.UserRepository
import com.shahrohit.chat.utils.ApiErrorHandler
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api : UserApiService
) : UserRepository {
    override suspend fun uploadPublicKey(request: PublicKeyRequest): Result<UploadKeyResponse> {
        return try{
            val response = api.uploadKey(request)
            Result.success(response);
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun pingUser() : Result<PingResponse> {
        return try{
            val response = api.pingUser()
            Result.success(response)
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun searchUser(query : String): Result<List<UserProfile>> {
        return try {
            val response = api.searchUsers(query);
            Result.success(response)
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}