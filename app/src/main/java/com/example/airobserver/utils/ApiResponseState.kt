package com.example.airobserver.utils

import android.app.Activity
import android.os.Build
import android.os.Message
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView
import com.example.airobserver.domain.model.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class ApiResponseStates<out T> {
    data class Success<out R>(val value: R?) : ApiResponseStates<R>()
    sealed class Failure : ApiResponseStates<Nothing>() {
        data class Network(val throwable: Throwable) : Failure()
        data class Validation(val message: Map<String,Boolean>) : Failure()
    }
    data class Loading(val isLoading : Boolean) : ApiResponseStates<Nothing>()
}

fun <T> startApiCall(
    apiCall: suspend () -> T
): Flow<ApiResponseStates<T>> =
    flow {
        emit(ApiResponseStates.Loading(true))
        try {
            val response = apiCall.invoke()
            emit(ApiResponseStates.Success(response))
            emit(ApiResponseStates.Loading(false))
        } catch (throwable: Throwable) {
            emit(ApiResponseStates.Failure.Network(throwable))
            emit(ApiResponseStates.Loading(false))
        }
    }

@RequiresApi(Build.VERSION_CODES.M)
fun <B : BaseResponse<T>, T> dataResponseHandling(
    activity: Activity,
    it: ApiResponseStates<B>,
    progressBar: LottieAnimationView,
    tryAgain: () -> Unit,
    successFunc: (T) -> Unit,
    errorCode: Int? = 0, handleError: (() -> Unit)? = null,
) {
    when (it) {
        is ApiResponseStates.Success -> {
            progressBar?.let { it1 -> hideProgress(it1) }
            it.value?.let {
                (it as BaseResponse<T>).handle(activity, successFunc)
            }
        }
        is ApiResponseStates.Loading -> {
            progressBar?.let { it1 -> showProgress(it1) }
        }
        is ApiResponseStates.Failure.Network -> {
            progressBar?.let { it1 -> hideProgress(it1) }
            showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

        }

        else -> {}
    }
}