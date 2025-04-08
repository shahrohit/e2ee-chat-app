package com.shahrohit.chat.local.repositories

import com.shahrohit.chat.local.models.UserEntity

interface LocalUserRepository {
    suspend fun saveUser(user: UserEntity)
    suspend fun getUserById(userId : Long) : UserEntity?
    suspend fun clearUser()
}