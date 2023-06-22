package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class AqiGraphHistoryResponse(
    @SerializedName("month" ) var month : String?         = null,
    @SerializedName("days"  ) var days  : ArrayList<Days> = arrayListOf(),
    @SerializedName("daysDetails" ) var daysDetails : ArrayList<DaysDetails> = arrayListOf()
)

data class Days (
    @SerializedName("day" ) var day : String? = null,
    @SerializedName("AQI" ) var MAX : String? = null
)

data class DaysDetails (
    @SerializedName("Date" ) var pollutantDate : String? = null,
    @SerializedName("PM10"           ) var PM10          : String? = null,
    @SerializedName("SO2"            ) var SO2           : String? = null,
    @SerializedName("CO"             ) var CO            : String? = null,
    @SerializedName("O3"             ) var O3            : String? = null,
    @SerializedName("NO2"            ) var NO2           : String? = null,
    @SerializedName("AQI"            ) var MAX           : String? = null,
    @SerializedName("Critical"       ) var Critical      : String? = null,
    @SerializedName("Category"       ) var Category      : String? = null
)
