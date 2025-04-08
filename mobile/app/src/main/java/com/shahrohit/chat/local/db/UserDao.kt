package com.shahrohit.chat.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahrohit.chat.local.models.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Long): UserEntity?

    @Query("DELETE FROM users")
    suspend fun clearAllUsers()
}