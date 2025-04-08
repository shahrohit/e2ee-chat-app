package com.shahrohit.chat.remote.dto

data class RegisterRequest(
    val name: String,
    val email : String,
    val username : String,
    val password : String
)