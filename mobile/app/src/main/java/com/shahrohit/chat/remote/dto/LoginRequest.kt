package com.shahrohit.chat.remote.dto

data class LoginRequest(
    val identifier : String,
    val password : String,
    val deviceFingerprint : String
);