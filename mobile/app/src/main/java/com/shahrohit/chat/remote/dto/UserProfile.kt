package com.shahrohit.chat.remote.dto

import com.shahrohit.chat.enums.FriendStatus

data class UserProfile(
    val name : String,
    val username : String,
    val profilePictureUrl : String?,
    val friendStatus : FriendStatus,
    val responseMessage : String?
)