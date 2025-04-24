package com.shahrohit.chat.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.MasterKey

object PreferenceManager {

    private const val PREF_NAME = "secure_prefs"
    private const val ONBOARDING_KEY = "onboarding_completed"
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"
    private const val USERID_KEY = "user_id"
    private const val DEVICE_FINGERPRINT_KEY = "device_fingerprint"


    private lateinit var prefs : SharedPreferences

    fun init(context : Context) {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            prefs = EncryptedSharedPreferences.create(
                context,
                PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (_: Exception) {
            context.deleteSharedPreferences(PREF_NAME)
            init(context)
        }
    }

    fun setDeviceFingerprint(fingerPrint: String) {
        prefs.edit { putString(DEVICE_FINGERPRINT_KEY, fingerPrint) }
    }

    fun getDeviceFingerprint() : String? {
        return prefs.getString(DEVICE_FINGERPRINT_KEY, null)
    }

    fun setOnBoardingCompleted(value: Boolean){
        prefs.edit() { putBoolean(ONBOARDING_KEY, value) }
    }

    fun isOnBoardingCompleted() : Boolean {
        return prefs.getBoolean(ONBOARDING_KEY,false);
    }

    fun saveToken(accessToken: String, refreshToken: String) {
        prefs.edit() {
            putString(ACCESS_TOKEN_KEY, accessToken).putString(
                REFRESH_TOKEN_KEY,
                refreshToken
            )
        }
    }

    fun getAccessToken(): String? = prefs.getString(ACCESS_TOKEN_KEY, null)
    fun getRefreshToken(): String? = prefs.getString(REFRESH_TOKEN_KEY, null)

    fun setUserId(userId : Long)=  prefs.edit() { putLong(USERID_KEY, userId) }
    fun getUserId(): Long? {
        val id = prefs.getLong(USERID_KEY, -1L)
        return if (id != -1L) id else null
    }

    fun isLoggedIn() : Boolean {
        return getAccessToken() != null && getRefreshToken() != null
    }

    fun clearTokens(){
        prefs.edit() { remove(ACCESS_TOKEN_KEY).remove(REFRESH_TOKEN_KEY) }
    }

    fun clearAll() = prefs.edit() { clear() }

}