package com.example.taskmanager.network

import okhttp3.ResponseBody

sealed class NetworkState<out T> {

    data object Loading: NetworkState<Nothing>()

    data class Success<out T>(val data: T?) : NetworkState<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val errorMessage: String?
    ) : NetworkState<Nothing>()
}