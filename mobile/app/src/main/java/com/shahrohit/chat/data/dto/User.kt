package com.shahrohit.chat.data.dto

data class User(
    val userId : Long,
    val name : String,
    val about : String?,
    val email : String,
    val username : String,
    val profilePictureUrl : String?,
)