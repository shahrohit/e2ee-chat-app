package com.shahrohit.chat.remote.dto

data class AuthResponse(
    val accessToken : String,
    val refreshToken : String,
    val user : User,
    val status : String,
    val message : String,
)