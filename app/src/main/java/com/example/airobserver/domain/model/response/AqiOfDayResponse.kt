package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class AqiOfDayResponse(
    @SerializedName("id"            ) var id            : String? = null,
    @SerializedName("Date"          ) var Date          : String? = null,
    @SerializedName("Time"          ) var Time          : String? = null,
    @SerializedName("NO2"           ) var NO2           : String? = null,
    @SerializedName("CO"            ) var CO            : String? = null,
    @SerializedName("O3"            ) var O3            : String? = null,
    @SerializedName("PM10"          ) var PM10          : String? = null,
    @SerializedName("PM25"          ) var PM25          : String? = null,
    @SerializedName("SO2"           ) var SO2           : String? = null,
    @SerializedName("Max"           ) var Max           : String? = null,
    @SerializedName("CriticalValue" ) var CriticalValue : String? = null,
    @SerializedName("Category"      ) var Category      : String? = null,
    @SerializedName("Note"          ) var Note          : String? = null
)
