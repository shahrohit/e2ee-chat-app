package com.shahrohit.chat.remote.dto

data class VerifyOtpRequest(
    val username : String,
    val otp : String,
    val deviceFingerprint : String
)