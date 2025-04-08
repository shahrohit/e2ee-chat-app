package com.shahrohit.chat.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shahrohit.chat.local.models.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
}