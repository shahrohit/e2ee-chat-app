package com.shahrohit.chat.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shahrohit.chat.enums.FriendRequestTab
import com.shahrohit.chat.remote.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.shahrohit.chat.remote.dto.UserProfile
import com.shahrohit.chat.utils.ApiRequestState
import kotlinx.coroutines.launch

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var selectedTab by mutableStateOf(FriendRequestTab.Friend)

    var receivedRequests by mutableStateOf<List<UserProfile>>(emptyList())
    var sentRequests by mutableStateOf<List<UserProfile>>(emptyList())
    var friends by mutableStateOf<List<UserProfile>>(emptyList())

    var respondToRequestState = mutableStateOf<ApiRequestState<Boolean?>>(ApiRequestState.Idle)
    var cancelRequestState = mutableStateOf<ApiRequestState<Boolean?>>(ApiRequestState.Idle)

    var itemLoadingState = mutableStateMapOf<String, Boolean>()

    fun onTabSelected(tab : FriendRequestTab){
        selectedTab = tab
    }

    fun getFriends(){
        viewModelScope.launch {
            val result = repository.getFriends()
            result.onSuccess { friends = it }
            result.onFailure {  Log.d("CONSOLE", it.message.toString()) }
        }
    }

    fun getReceivedFriendRequests(){
        viewModelScope.launch {
            val result = repository.getReceivedFriendRequests()
            result.onSuccess { receivedRequests = it }
            result.onFailure {  Log.d("CONSOLE", it.message.toString()) }
        }
    }

    fun getSentFriendRequests(){
        viewModelScope.launch {
            val result = repository.getSentFriendRequests()
            result.onSuccess { sentRequests = it }
            result.onFailure { Log.d("CONSOLE", it.message.toString()) }
        }
    }

    fun respondToFriendRequest(username: String, accepted: Boolean){
        viewModelScope.launch {
            itemLoadingState[username] = true
            val result = repository.responseToFriendRequest(username, accepted)
            result.onSuccess {
                respondToRequestState.value = ApiRequestState.Success(accepted)
                receivedRequests = receivedRequests.map {
                    if(it.username == username) {
                        it.copy(responseMessage = if(accepted) "You are now connected" else "Request Rejected")
                    } else {
                        it
                    }
                }
            }
            result.onFailure { respondToRequestState.value  = ApiRequestState.Error(it.message.toString()) }
            itemLoadingState[username] = false
        }
    }

    fun cancelFriendRequest(username: String){
        viewModelScope.launch {
            itemLoadingState[username] = true
            val result = repository.cancelFriendRequest(username)
            result.onSuccess {
                cancelRequestState.value = ApiRequestState.Success(it)
                sentRequests = sentRequests.map {
                    if(it.username == username) it.copy(responseMessage = "Request Cancelled") else it
                }
            }
            result.onFailure { cancelRequestState.value = ApiRequestState.Error(it.message.toString()) }
            itemLoadingState[username] = false
        }
    }



}