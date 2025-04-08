package com.shahrohit.chat.remote.dto

data class PublicKeyRequest(
    val userId : Long,
    val publicKey : String,
    val deviceFingerprint : String
)