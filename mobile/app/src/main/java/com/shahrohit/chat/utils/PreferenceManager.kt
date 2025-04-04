package com.shahrohit.chat.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object PreferenceManager {
    private const val PREF_NAME = "secure_prefs"
    private const val ONBOARDING_KEY = "onboarding_completed"

    private lateinit var prefs : EncryptedSharedPreferences

    fun init(context: Context){
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

    fun clearAll(){
        prefs.edit().clear().apply()
    }

}