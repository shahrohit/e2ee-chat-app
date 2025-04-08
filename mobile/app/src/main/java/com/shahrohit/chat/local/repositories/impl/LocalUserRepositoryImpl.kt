package com.shahrohit.chat.local.repositories.impl

import com.shahrohit.chat.local.db.UserDao
import com.shahrohit.chat.local.models.UserEntity
import com.shahrohit.chat.local.repositories.LocalUserRepository
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): LocalUserRepository{
    override suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun getUserById(userId : Long): UserEntity? {
        return userDao.getUserById(userId)
    }

    override suspend fun clearUser() {
        userDao.clearAllUsers()
    }

}