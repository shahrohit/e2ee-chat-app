package com.shahrohit.chat.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.local.models.UserEntity
import com.shahrohit.chat.local.repositories.LocalUserRepository
import com.shahrohit.chat.remote.dto.ApiException
import com.shahrohit.chat.remote.repository.UserRepository
import com.shahrohit.chat.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository,
    private val remoteUserRepository : UserRepository,
) : ViewModel(){

    private val _userState = mutableStateOf<UserEntity?>(null)
    val userState: State<UserEntity?> = _userState

    init {
        loadUser()
    }

    private fun loadUser() {
        val userId = PreferenceManager.getUserId() ?: return
        viewModelScope.launch {
            val user = localUserRepository.getUserById(userId)
            _userState.value = user
        }
    }

    fun ping(){
        viewModelScope.launch {
            val result = remoteUserRepository.pingUser();
            result.onSuccess { response ->
                Log.d("CONSOLE", "SUCCESS: $response")
            }
            result.onFailure { errResponse ->
                    Log.d("CONSOLE", "ERROR: ${errResponse.message}")
            }
        }
    }
}