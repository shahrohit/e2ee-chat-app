package com.shahrohit.chat.remote.dto

data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val errorData: Map<String, Any>?
)