package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class AqiGraphLastHoursResponse(
    @SerializedName("Date" ) var Date : String? = null,
    @SerializedName("Time" ) var Time : String? = null,
    @SerializedName("AQI"  ) var AQI  : String? = null
)
