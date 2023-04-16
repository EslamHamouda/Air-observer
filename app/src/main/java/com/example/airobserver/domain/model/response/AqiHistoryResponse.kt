package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class AqiHistoryResponse(
    @SerializedName("daysDetails" ) var daysDetails : ArrayList<AqiHistoryDetailsResponse> = arrayListOf()
)
data class AqiHistoryDetailsResponse(
    @SerializedName("pollutant_date" ) var pollutantDate : String? = null,
    @SerializedName("PM10"           ) var PM10          : String? = null,
    @SerializedName("SO2"            ) var SO2           : String? = null,
    @SerializedName("CO"             ) var CO            : String? = null,
    @SerializedName("O3"             ) var O3            : String? = null,
    @SerializedName("NO2"            ) var NO2           : String? = null,
    @SerializedName("MAX"            ) var MAX           : String? = null,
    @SerializedName("Critical"       ) var Critical      : String? = null,
    @SerializedName("Category"       ) var Category      : String? = null
)

