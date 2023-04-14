package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class AqiGraphHistoryResponse(
    @SerializedName("month" ) var month : String?         = null,
    @SerializedName("days"  ) var days  : ArrayList<Days> = arrayListOf()
)

data class Days (
    @SerializedName("day" ) var day : String? = null,
    @SerializedName("MAX" ) var MAX : String? = null
)
