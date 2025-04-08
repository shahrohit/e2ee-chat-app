package com.shahrohit.chat.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import java.security.MessageDigest

object DeviceFingerprint {

    @SuppressLint("HardwareIds")
    fun generate(context: Context): String{
        if(PreferenceManager.getDeviceFingerprint() != null){
            return PreferenceManager.getDeviceFingerprint()!!
        }

        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        val rawFingerprint = listOf(
            Build.BRAND,
            Build.DEVICE,
            Build.MODEL,
            Build.PRODUCT,
            Build.MANUFACTURER,
            Build.PRODUCT,
            Build.VERSION.SDK_INT.toString(),
            androidId
        ).joinToString(separator = "|")

        Log.d("CONSOLE", "Raw Fingerprint: $rawFingerprint")
        val hashedFingerprint = sha256(rawFingerprint)
        PreferenceManager.setDeviceFingerprint(hashedFingerprint)
        return hashedFingerprint;
    }

    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") {"%02x".format(it)}
    }
}