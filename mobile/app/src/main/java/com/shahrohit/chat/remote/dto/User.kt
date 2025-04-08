package com.shahrohit.chat.remote.dto

data class User(
    val userId : Long,
    val name : String,
    val about : String?,
    val email : String,
    val username : String,
    val profilePictureUrl : String?,
)