package com.shahrohit.chat.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecureTokenManager {

    private const val PREF_NAME = "secure_prefs"
    private const val ONBOARDING_KEY = "onboarding_completed"
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"
    private const val USERID_KEY = "user_id"


    private lateinit var prefs : SharedPreferences

    fun init(context : Context) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        prefs = EncryptedSharedPreferences.create(
            PREF_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences;
    }

    fun setOnBoardingCompleted(value: Boolean){
        prefs.edit().putBoolean(ONBOARDING_KEY,value).apply()
    }

    fun isOnBoardingCompleted() : Boolean {
        return prefs.getBoolean(ONBOARDING_KEY,false);
    }

    fun setAccessToken(token: String) = prefs.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    fun getAccessToken(): String? = prefs.getString(ACCESS_TOKEN_KEY, null)
    fun setRefreshToken(token: String) = prefs.edit().putString(REFRESH_TOKEN_KEY, token).apply()
    fun getRefreshToken(): String? = prefs.getString(REFRESH_TOKEN_KEY, null)

    fun setUserId(userId : Long)=  prefs.edit().putLong(USERID_KEY, userId).apply()
    fun getUserId(): Long? {
        val id = prefs.getLong(USERID_KEY, -1L)
        return if (id != -1L) id else null
    }

    fun clearTokens(){
        prefs.edit().remove(ACCESS_TOKEN_KEY).remove(REFRESH_TOKEN_KEY).apply()
    }

    fun clearAll() = prefs.edit().clear().apply()
}