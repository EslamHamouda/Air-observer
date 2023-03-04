package com.example.airobserver.domain.model

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("code") var code: Int? = 0,
    @SerializedName("message") var message: String? = "",
    @SerializedName("data") var data: T? = null
)