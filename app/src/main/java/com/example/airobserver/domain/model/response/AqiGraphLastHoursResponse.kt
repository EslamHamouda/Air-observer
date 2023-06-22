package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class AqiGraphLastHoursResponse(
    @SerializedName("Time" ) var hour : String? = null,
    @SerializedName("AQI"  ) var MAX  : String? = null
)
