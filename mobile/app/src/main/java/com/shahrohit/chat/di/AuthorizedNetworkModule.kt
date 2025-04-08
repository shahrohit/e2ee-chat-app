package com.shahrohit.chat.di

import com.shahrohit.chat.BuildConfig
import com.shahrohit.chat.remote.api.UserApiService
import com.shahrohit.chat.remote.repository.AuthRepository
import com.shahrohit.chat.remote.repository.UserRepository
import com.shahrohit.chat.remote.repository.impl.UserRepositoryImpl
import com.shahrohit.chat.utils.PreferenceManager
import com.shahrohit.chat.utils.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthorizedNetworkModule {

    @Provides
    @Authenticated
    fun provideBaseUrl() : String = "${BuildConfig.BASE_URL}/api/"

    @Provides
    @Singleton
    @Authenticated
    fun provideAuthorizationHttpClient(authRepository: AuthRepository) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = PreferenceManager.getAccessToken()
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .authenticator(TokenAuthenticator(authRepository))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Authenticated
    fun provideAuthorizedRetrofit(@Authenticated baseUrl : String, @Authenticated client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApiService(@Authenticated retrofit: Retrofit) : UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApiService): UserRepository = UserRepositoryImpl(api)
}