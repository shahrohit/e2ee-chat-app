package com.shahrohit.chat.data.dto

data class VerifyOtpRequest(
    val username : String,
    val otp : String,
    val deviceFingerprint : String
)