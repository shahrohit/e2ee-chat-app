package com.shahrohit.chat.local.adapters

import com.shahrohit.chat.local.models.UserEntity
import com.shahrohit.chat.remote.dto.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = userId,
        name = this.name,
        about = this.about,
        email = this.email,
        username = this.username,
        profilePictureUrl = this.profilePictureUrl
    )
}

fun UserEntity.toUser() : User {
    return User(
        userId = this.userId,
        name = this.name,
        about = this.about,
        email = this.email,
        username = this.username,
        profilePictureUrl = this.profilePictureUrl
    )
}