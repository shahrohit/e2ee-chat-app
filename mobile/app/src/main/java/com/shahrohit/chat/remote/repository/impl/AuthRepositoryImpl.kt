package com.shahrohit.chat.remote.repository.impl

import com.shahrohit.chat.enums.OtpFor
import com.shahrohit.chat.remote.api.AuthApiService
import com.shahrohit.chat.remote.dto.AccessTokenRequest
import com.shahrohit.chat.remote.dto.AuthResponse
import com.shahrohit.chat.remote.dto.LoginRequest
import com.shahrohit.chat.remote.dto.RegisterRequest
import com.shahrohit.chat.remote.dto.UserAvailabilityResponse
import com.shahrohit.chat.remote.dto.VerifyOtpRequest
import com.shahrohit.chat.remote.repository.AuthRepository
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

    override suspend fun verifyOtp(request: VerifyOtpRequest, otpFor: OtpFor): Result<AuthResponse> {
        return try {
            val response = if (otpFor == OtpFor.USER)  api.verifyUser(request) else api.verifyDevice(request)
            return Result.success(response);
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun refreshAccessToken(request: AccessTokenRequest): Result<AuthResponse> {
        return try {
            val response = api.refreshAccessToken(request)
            return Result.success(response);
        } catch (e : HttpException){
            Result.failure(ApiErrorHandler.parseHttpException(e))
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}