package com.shahrohit.chat.utils

import com.google.gson.Gson
import com.shahrohit.chat.data.dto.ApiException
import com.shahrohit.chat.data.dto.ErrorResponse
import retrofit2.HttpException

object ApiErrorHandler {
    fun parseHttpException(e: HttpException): ApiException {
        val errorBody = e.response()?.errorBody()?.string();
        val gson = Gson()
        val errorResponse = try {
            gson.fromJson(errorBody, ErrorResponse::class.java)
        } catch (parseException : Exception){
            null
        }

        return ApiException(
            statusCode = errorResponse?.statusCode ?: 500,
            message = errorResponse?.message ?: "Internal Server Error",
            errorData = errorResponse?.errorData
        )
    }
}