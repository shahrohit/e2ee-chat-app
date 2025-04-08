package com.shahrohit.chat.di

import android.content.Context
import androidx.room.Room
import com.shahrohit.chat.local.db.AppDatabase
import com.shahrohit.chat.local.db.UserDao
import com.shahrohit.chat.local.repositories.LocalUserRepository
import com.shahrohit.chat.local.repositories.impl.LocalUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "chat_app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao
    ) : LocalUserRepository = LocalUserRepositoryImpl(userDao)

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()
}