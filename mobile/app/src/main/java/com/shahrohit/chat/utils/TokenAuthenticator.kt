package com.shahrohit.chat.utils

import com.shahrohit.chat.remote.dto.AccessTokenRequest
import com.shahrohit.chat.remote.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val authRepository: AuthRepository
) : Authenticator{
    override fun authenticate(route: Route?, response: Response): Request? {

        if(responseCount(response) >= 2) return null

        val newAccessToken = getAccessToken();
        if(newAccessToken == null){
            AuthManager.notifyAuthFailure()
            return null
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }

    private fun getAccessToken() : String? {
        val  refreshToken = PreferenceManager.getRefreshToken() ?: return null
        val deviceFingerprint = PreferenceManager.getDeviceFingerprint() ?: return null
        val request = AccessTokenRequest(
            refreshToken = refreshToken,
            deviceFingerprint = deviceFingerprint
        )

        return try {
            runBlocking {
                val result = authRepository.refreshAccessToken(request)
                result.onSuccess {
                    PreferenceManager.saveToken(it.accessToken, it.refreshToken)
                }
                result.getOrNull()?.accessToken
            }
        }catch (_: Exception){
            return null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var current = response
        while (current.priorResponse != null) {
            count++
            current = current.priorResponse!!
        }
        return count
    }
}