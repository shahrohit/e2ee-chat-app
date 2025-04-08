package com.shahrohit.chat.di

import com.shahrohit.chat.BuildConfig
import com.shahrohit.chat.remote.api.AuthApiService
import com.shahrohit.chat.remote.repository.AuthRepository
import com.shahrohit.chat.remote.repository.impl.AuthRepositoryImpl
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
    @Unauthenticated
    fun provideBaseUrl(): String = "${BuildConfig.BASE_URL}/api/"

    @Provides
    @Singleton
    @Unauthenticated
    fun provideRetrofit(@Unauthenticated baseUrl : String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(@Unauthenticated retrofit: Retrofit) : AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApiService): AuthRepository = AuthRepositoryImpl(api)
}