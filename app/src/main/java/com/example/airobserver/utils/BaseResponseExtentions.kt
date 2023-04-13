package com.example.airobserver.utils

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.airobserver.domain.model.BaseResponse



@RequiresApi(Build.VERSION_CODES.M)
fun <T> BaseResponse<T>.handle(activity: Activity, successFunc: (T) -> Unit) {
    when (code) {
        200 ->
            data?.let {
                successFunc.invoke(it)
            }
        else -> message?.let {
            showSnackbar(it, activity)
        }
    }
}