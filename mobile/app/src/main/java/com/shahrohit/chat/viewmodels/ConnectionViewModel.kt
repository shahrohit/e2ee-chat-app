package com.shahrohit.chat.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.remote.dto.AuthResponse
import com.shahrohit.chat.remote.dto.PublicKeyRequest
import com.shahrohit.chat.remote.dto.UploadKeyResponse
import com.shahrohit.chat.remote.repository.UserRepository
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.utils.DeviceFingerprint
import com.shahrohit.chat.utils.PreferenceManager
import com.shahrohit.chat.utils.generateKeyPair
import com.shahrohit.chat.utils.getPublicKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var connectionState = mutableStateOf<ApiRequestState<UploadKeyResponse>>(ApiRequestState.Loading)

    private fun handleSuccess(response: UploadKeyResponse){
        Log.d("CONSOLE", "SUCCESS")
        connectionState.value = ApiRequestState.Success(response)
    }

    private fun handleFailure(errResponse: Throwable){
        connectionState.value = ApiRequestState.Error(errResponse.message ?: "Failed to established connection")
    }

    fun establishConnection(context: Context){
        val userId = PreferenceManager.getUserId();
        if(userId == null){
            connectionState.value = ApiRequestState.Error("User Not Found")
            return;
        }

        generateKeyPair()

        val publicKey = getPublicKey();
        if(publicKey == null){
            connectionState.value = ApiRequestState.Error("Public Key Not Found")
            return;
        }

        viewModelScope.launch {

            try {
                val deviceFingerprint = DeviceFingerprint.generate(context)
                val request = PublicKeyRequest(userId = userId,publicKey = publicKey,deviceFingerprint = deviceFingerprint)
                val result = userRepository.uploadPublicKey(request)

                result.onSuccess { handleSuccess(it) }
                result.onFailure { handleFailure(it) }

            }catch (e: Exception){

            }
        }
    }
}