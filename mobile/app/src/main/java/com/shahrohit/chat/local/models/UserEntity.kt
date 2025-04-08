package com.shahrohit.chat.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userId : Long,
    val name : String,
    val about : String?,
    val email : String,
    val username : String,
    val profilePictureUrl : String?
)