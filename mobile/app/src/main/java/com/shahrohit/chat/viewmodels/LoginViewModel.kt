package com.shahrohit.chat.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.data.dto.AuthResponse
import com.shahrohit.chat.data.dto.LoginRequest
import com.shahrohit.chat.domain.repository.AuthRepository
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.utils.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
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

    fun login(){
        if (!validate()) return

        viewModelScope.launch {
            loginState.value = ApiRequestState.Loading
            try {
                val request = LoginRequest(
                    identifier = identifier,
                    password = password,
                    deviceFingerprint = "Redmi Note 9"
                )
                val result = repository.login(request)

                result.onSuccess { response ->
                    loginState.value = ApiRequestState.Success(response)
                }.onFailure { errResponse ->
                    loginState.value = ApiRequestState.Error(errResponse.message ?: "Login failed")
                }

            }catch (e: Exception){
                loginState.value = ApiRequestState.Error("Internal Server Error")

            }
        }
    }
}