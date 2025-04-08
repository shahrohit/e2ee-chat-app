package com.shahrohit.chat.data.dto

data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val errorData: Map<String, Any>?
)