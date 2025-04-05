package com.shahrohit.chat.di

import com.shahrohit.chat.BuildConfig
import com.shahrohit.chat.data.api.AuthApiService
import com.shahrohit.chat.domain.repository.AuthRepository
import com.shahrohit.chat.domain.repository.impl.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = "${BuildConfig.BASE_URL}/api/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit) : AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApiService
    ): AuthRepository = AuthRepositoryImpl(api)
}