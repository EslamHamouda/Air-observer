package com.example.airobserver.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class ApiResponseStates<out T> {
    data class Success<out R>(val value: R?) : ApiResponseStates<R>()
    data class NetworkError(val throwable: Throwable) : ApiResponseStates<Nothing>()
    object Loading : ApiResponseStates<Nothing>()
}

fun <T> startApiCall(
    apiCall: suspend () -> T
): Flow<ApiResponseStates<T>> =
    flow {
        emit(ApiResponseStates.Loading)
        try {
            val response = apiCall.invoke()
            emit(ApiResponseStates.Success(response))
        } catch (throwable: Throwable) {
            emit(ApiResponseStates.NetworkError(throwable))
        }
    }
