package com.shahrohit.chat.data.repository.impl

import com.shahrohit.chat.data.api.AuthApiService
import com.shahrohit.chat.data.dto.AuthResponse
import com.shahrohit.chat.data.dto.LoginRequest
import com.shahrohit.chat.data.dto.RegisterRequest
import com.shahrohit.chat.data.dto.UserAvailabilityResponse
import com.shahrohit.chat.data.dto.VerifyOtpRequest
import com.shahrohit.chat.data.repository.AuthRepository
import com.shahrohit.chat.utils.ApiErrorHandler
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api : AuthApiService
) : AuthRepository {

    override suspend fun register(request: RegisterRequest): Result<AuthResponse> {
        return try{
            val response = api.registerUser(request)
            Result.success(response);
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun checkUsername(username: String): Result<UserAvailabilityResponse> {
        return try {
            val response = api.checkUsernameAvailability(username);
            Result.success(response)
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return try {
            val response = api.login(request)
            return Result.success(response)
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun verifyOtp(request: VerifyOtpRequest): Result<AuthResponse> {
        return try {
            val response = api.verifyOtp(request)
            return Result.success(response);
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}