package com.shahrohit.chat.utils

sealed class ApiRequestState<out T> {
    data object Idle : ApiRequestState<Nothing>()
    data object Loading : ApiRequestState<Nothing>()
    data class Success<T>(val data: T) : ApiRequestState<T>()
    data class Error(val message: String) : ApiRequestState<Nothing>()
}