package com.shahrohit.chat.remote.dto

data class AccessTokenRequest(
    val refreshToken : String,
    val deviceFingerprint : String
)