package com.shahrohit.chat.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.remote.dto.UserProfile
import com.shahrohit.chat.remote.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    var query by mutableStateOf("")
    var users by mutableStateOf<List<UserProfile>>(emptyList())

    var requestSent by mutableStateOf(false)

    var checkingStatus by mutableStateOf<Boolean?>(null)
    var addFriendLoading by mutableStateOf(false)

    private var debounceJob: Job? = null
    fun onQueryChange(newQuery : String){
        query = newQuery


        debounceJob?.cancel();

        if(query.isEmpty()) {
            users = emptyList()
            return
        };
        debounceJob= viewModelScope.launch {
            delay(500L);

            checkingStatus = true
            val result = repository.searchUser(query);

            result.onSuccess { response ->
               users = response
            }

            result.onFailure {
                Log.d("CONSOLE", it.message.toString())
            }

            checkingStatus = false

        }
    }

    fun sendFriendRequest(username : String){
        viewModelScope.launch {
            addFriendLoading = true
            val result = repository.sendFriendRequest(username)
            result.onSuccess {
                requestSent = it

            }
            result.onFailure { Log.d("CONSOLE", it.message.toString()) }
            addFriendLoading = false
        }
    }
}