package com.example.airobserver.domain.model

import com.example.airobserver.domain.model.response.LoginResponse
import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("Code") var code: Int? = 0,
    @SerializedName("message") var message: String? = "",
    @SerializedName("data") var data: T? = null
)
