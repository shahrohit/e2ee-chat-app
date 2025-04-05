package com.shahrohit.chat.data.dto

data class AuthResponse(
    val accessToken : String,
    val refreshToken : String,
    val user : User,
    val message : String,
)