package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class AqiGraphLastHoursResponse(
    @SerializedName("hour" ) var hour : String?         = null,
    @SerializedName("MAX"  ) var MAX  : String? = null
)
