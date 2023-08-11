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
    data class Failure(val throwable: Throwable) : ApiResponseStates<Nothing>()
    data class ValidationFailure(val message: String) : ApiResponseStates<Nothing>()
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
            emit(ApiResponseStates.Failure(throwable))
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
        is ApiResponseStates.Failure -> {
            progressBar?.let { it1 -> hideProgress(it1) }
            showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

        }

        else -> {}
    }
}