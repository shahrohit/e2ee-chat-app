package com.shahrohit.chat.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.enums.OtpFor
import com.shahrohit.chat.local.adapters.toEntity
import com.shahrohit.chat.local.repositories.LocalUserRepository
import com.shahrohit.chat.remote.dto.AuthResponse
import com.shahrohit.chat.remote.dto.VerifyOtpRequest
import com.shahrohit.chat.remote.repository.AuthRepository
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.utils.DeviceFingerprint
import com.shahrohit.chat.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userRepository: LocalUserRepository
) : ViewModel() {

    private val _otpDigits = mutableStateListOf("", "", "", "", "", "")
    val otpDigits: List<String> get() = _otpDigits

    var verifyOtpState = mutableStateOf<ApiRequestState<AuthResponse>>(ApiRequestState.Idle)
    var errorMessage by mutableStateOf<String?>(null)

    fun onOtpDigitChanged(index : Int, value : String){
        if(index in 0..5){
            _otpDigits[index] = value
        }
    }

    private suspend fun handleSuccess(response: AuthResponse){
        verifyOtpState.value = ApiRequestState.Success(response)
        PreferenceManager.saveToken(response.accessToken, response.refreshToken)
        PreferenceManager.setUserId(response.user.userId)
        userRepository.saveUser(response.user.toEntity())
    }

    private fun handleFailure(errResponse: Throwable){
        verifyOtpState.value = ApiRequestState.Error(errResponse.message ?: "OTP verification Failed")
    }

    fun verifyOtp(context: Context, username : String, otpFor : OtpFor){
        val otp = _otpDigits.joinToString("")
        if(otp.length != 6){
            errorMessage = "Enter 6-digit OTP"
            return
        }

        viewModelScope.launch {
            verifyOtpState.value = ApiRequestState.Loading
            try {
                val request = VerifyOtpRequest(
                    username = username,
                    otp = otp,
                    deviceFingerprint = DeviceFingerprint.generate(context)
                )
                val result = repository.verifyOtp(request, otpFor)

                result.onSuccess { handleSuccess(it) }
                result.onFailure { handleFailure(it) }

            }catch (_: Exception){
                verifyOtpState.value = ApiRequestState.Error("Network Error")

            }
        }
    }


}