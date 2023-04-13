package com.example.airobserver.ui

import android.app.Activity
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.utils.*


open class BaseFragment: Fragment() {
    @RequiresApi(Build.VERSION_CODES.M)
    open fun <B : BaseResponse<T>, T> dataResponseHandling(
        activity: Activity,
        it: ApiResponseStates<B>,
        progressBar: ProgressBar?,
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
            is ApiResponseStates.NetworkError -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                    showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

            }
        }
    }
}