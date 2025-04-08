package com.shahrohit.chat.data.dto

data class LoginRequest(
    val identifier : String,
    val password : String,
    val deviceFingerprint : String
);