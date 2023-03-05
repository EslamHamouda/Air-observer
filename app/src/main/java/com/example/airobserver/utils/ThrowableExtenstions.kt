package com.example.airobserver.utils

import android.content.Context
import android.util.Log
import com.example.airobserver.R
import com.example.airobserver.domain.model.BaseResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.handling(context: Context): String {
    return when (this) {
        is HttpException -> when (code()) {
            401 -> context.getString(R.string.not_authorized)
            //422 -> context.getString(R.string.unprocessable_content)
            else -> {
                try {
                    val error = Gson().fromJson(
                        response()?.errorBody()?.string(),
                        BaseResponse::class.java
                    )
                    if (error != null) "${error.message}"
                    else getGeneralErrorMessage(context)
                } catch (ex: JsonSyntaxException) {
                    getGeneralErrorMessage(context)
                }
            }
        }

        is SocketTimeoutException -> {
            context.getString(R.string.connection_timeout)
        }
        
        is UnknownHostException->context.getString(R.string.no_internet_connection)

        else -> {
            Log.e("", "Throwable: $message")
            message.toString()
        }
    }
}

fun Throwable.loginHandling(context: Context): String {
    return when (this) {
        is HttpException -> when (code()) {
            401 -> context.getString(R.string.not_authorized)
            //403 -> context.getString(R.string.unprocessable_content)
            //411 -> context.getString(R.string.app_name)
            else -> {
                try {
                    val error = Gson().fromJson(
                        response()?.errorBody()?.string(),
                        ErrorModel::class.java
                    )
                    if (error != null) "${error.error}:${error.errorDescription}"
                    else getLoginGeneralErrorMessage(context)
                } catch (ex: JsonSyntaxException) {
                    getLoginGeneralErrorMessage(context)
                }
            }
        }

        is SocketTimeoutException -> context.getString(R.string.connection_timeout)

        else -> {
            Log.e("", "Throwable: $message")
            message.toString()
        }
    }
}

private fun HttpException.getGeneralErrorMessage(context: Context): String {
    val msg = this.response()?.errorBody()?.string()
    return if (msg.isNullOrEmpty()) {
        Log.e("", "Throwable: $message")
        context.getString(R.string.connection_error)
    } else {
        Log.e("", "Throwable: $msg")
        msg.toString()
    }
}

private fun HttpException.getLoginGeneralErrorMessage(context: Context): String {
    val msg = this.response()?.errorBody()?.string()
    return if (msg.isNullOrEmpty()) {
        Log.e("", "Throwable: $message")
        context.getString(R.string.connection_error)
    } else {
        Log.e("", "Throwable: $msg")
        msg.toString()
    }
}

data class ErrorModel(
    @SerializedName("error") var error: String? = "",
    @SerializedName("error_description") var errorDescription: String? = ""
)
