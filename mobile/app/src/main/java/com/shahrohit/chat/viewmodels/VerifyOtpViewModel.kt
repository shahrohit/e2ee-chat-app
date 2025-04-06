package com.shahrohit.chat.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.data.dto.AuthResponse
import com.shahrohit.chat.data.dto.LoginRequest
import com.shahrohit.chat.data.dto.VerifyOtpRequest
import com.shahrohit.chat.domain.repository.AuthRepository
import com.shahrohit.chat.utils.ApiRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val repository: AuthRepository
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

    fun verifyOtp(username : String){
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
                    deviceFingerprint = "Redmi Note 9"
                )
                val result = repository.verifyOtp(request)

                result.onSuccess { response ->
                    verifyOtpState.value = ApiRequestState.Success(response)
                }.onFailure { errResponse ->
                    verifyOtpState.value = ApiRequestState.Error(errResponse.message ?: "OTP verification Failed")
                }

            }catch (e: Exception){
                verifyOtpState.value = ApiRequestState.Error("Network Error")

            }
        }
    }


}