package com.shahrohit.chat.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.enums.AuthStatus
import com.shahrohit.chat.local.adapters.toEntity
import com.shahrohit.chat.local.repositories.LocalUserRepository
import com.shahrohit.chat.remote.dto.AuthResponse
import com.shahrohit.chat.remote.dto.LoginRequest
import com.shahrohit.chat.remote.repository.AuthRepository
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.utils.DeviceFingerprint
import com.shahrohit.chat.utils.FormValidator
import com.shahrohit.chat.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userRepository: LocalUserRepository
) : ViewModel(){

    var identifier by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var identifierError by mutableStateOf<String?>(null)
    var loginState = mutableStateOf<ApiRequestState<AuthResponse>>(ApiRequestState.Idle)

    private fun validate(): Boolean {
        identifierError = FormValidator.validateIdentifier(identifier)
        return identifierError == null
    }

    fun onIdentifierChanged(value: String) {
        identifier = value
        identifierError = null
    }

    fun changePasswordVisibility(){
        passwordVisible = !passwordVisible
    }

    fun onPasswordChanged(value: String) {
        password = value
    }

    private suspend fun handleSuccess(response : AuthResponse){
        Log.d("CONSOLE", "Login Response: $response")
        if(response.status == AuthStatus.USER_VERIFIED.name){
            Log.d("CONSOLE", "VERIFIED")
            PreferenceManager.saveToken(response.accessToken, response.refreshToken)
            PreferenceManager.setUserId(response.user.userId)
            userRepository.saveUser(response.user.toEntity())
        }
        loginState.value = ApiRequestState.Success(response)
    }

    private fun handleFailure(errResponse: Throwable){
        loginState.value = ApiRequestState.Error(errResponse.message ?: "Login failed")
    }

    fun login(context : Context){
        if (!validate()) return
        viewModelScope.launch {
            loginState.value = ApiRequestState.Loading
            try {
                val deviceFingerprint = DeviceFingerprint.generate(context)
                val request = LoginRequest(identifier = identifier, password = password, deviceFingerprint = deviceFingerprint)
                val result = repository.login(request)

                result.onSuccess {  handleSuccess(it) }
                result.onFailure { handleFailure(it) }

            }catch (_: Exception){
                loginState.value = ApiRequestState.Error("Internal Server Error")
            }
        }
    }
}