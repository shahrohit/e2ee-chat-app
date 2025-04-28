package com.shahrohit.chat.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object AuthManager {
    private val _authFailed = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val authFailed: SharedFlow<Unit> = _authFailed

    fun notifyAuthFailure(){
        PreferenceManager.clearTokens()
        _authFailed.tryEmit(Unit)
    }
}