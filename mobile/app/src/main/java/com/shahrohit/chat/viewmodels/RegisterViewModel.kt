package com.shahrohit.chat.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.data.dto.ApiException
import com.shahrohit.chat.data.dto.RegisterRequest
import com.shahrohit.chat.domain.repository.AuthRepository
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.utils.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    var name by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var nameError by mutableStateOf<String?>(null)
    var usernameError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    var isPasswordVisible by mutableStateOf(false)
    var isUsernameAvailable by mutableStateOf<Boolean?>(null)
    var isCheckingUsername by mutableStateOf(false)

    val registerState = mutableStateOf<ApiRequestState<String>>(ApiRequestState.Idle)

    private var debounceJob: Job? = null

    fun onNameChanged(newName: String) {
        name = newName
        nameError = null
    }

    fun onEmailChanged(newEmail: String) {
        email = newEmail
        emailError = null
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
        passwordError = null
    }

    fun changePasswordVisibility(){
        isPasswordVisible = !isPasswordVisible
    }

    fun onUsernameChanged(newUsername: String) {
        username = newUsername
        usernameError= null
        isUsernameAvailable = null

        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(500L)

            val formatError = FormValidator.validateUsername(newUsername)
            usernameError = formatError

            if (formatError == null) {
                checkUsernameAvailability(newUsername)
            } else {
                isUsernameAvailable = null
            }
        }
    }

    private fun checkUsernameAvailability(username: String) {
        viewModelScope.launch {
            isCheckingUsername = true

            val result = repository.checkUsername(username)

            result.onSuccess { response ->
                isUsernameAvailable = response.available
                usernameError = response.message
            }.onFailure {
                isUsernameAvailable = null
                usernameError = "Error checking username"
            }

            isCheckingUsername = false
        }
    }

    private fun validate(): Boolean {
        nameError = FormValidator.validateName(name)
        emailError = FormValidator.validateEmail(email)
        usernameError = FormValidator.validateUsername(username)
        passwordError = FormValidator.validatePassword(password)
        return listOf(nameError,usernameError,emailError,passwordError).all { it == null } && isUsernameAvailable == true
    }


    fun register() {
        if(!validate()) return;

        viewModelScope.launch {
            registerState.value = ApiRequestState.Loading
            try {
                val request = RegisterRequest(name = name, email = email,username = username,password = password)
                val result = repository.register(request)

                result.onSuccess { response ->
                    registerState.value = ApiRequestState.Success(response.message)
                }.onFailure { errResponse ->
                    if(errResponse is ApiException){
                        val errorMap = errResponse.errorData.orEmpty()
                        nameError = errorMap["name"]?.toString()
                        usernameError = errorMap["username"]?.toString()
                        emailError = errorMap["email"]?.toString()
                        passwordError = errorMap["password"]?.toString()
                        registerState.value = ApiRequestState.Error(errResponse.message ?: "Registration failed")
                    }else{
                        registerState.value = ApiRequestState.Error(errResponse.message ?: "Registration failed")
                    }
                }


            } catch (e: Exception) {
                registerState.value = ApiRequestState.Error("Internal Server Error")
            }
        }
    }
}