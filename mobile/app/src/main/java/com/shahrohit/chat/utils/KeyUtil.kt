package com.shahrohit.chat.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore

fun generateKeyPair(){
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    if(!keyStore.containsAlias(KEY_ALIAS)){
        val keyPairGenerator = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_RSA,
            "AndroidKeyStore"
        )

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setKeySize(2048)
            setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
        }.build()

        keyPairGenerator.initialize(keyGenParameterSpec)
        keyPairGenerator.generateKeyPair()
    }
}

fun getPublicKey() : String? {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    if(!keyStore.containsAlias(KEY_ALIAS)) return null
    val publicKey = keyStore.getCertificate(KEY_ALIAS).publicKey
    val publicKeyBytes = publicKey.encoded
    return Base64.encodeToString(publicKeyBytes, Base64.NO_WRAP)
}