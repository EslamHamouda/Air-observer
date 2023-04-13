package com.example.airobserver.domain.model.response

import com.google.gson.annotations.SerializedName

data class GetProfileResponse (@SerializedName("firstname" ) var firstname : String? = null,
                               @SerializedName("lastname"  ) var lastname  : String? = null,
                               @SerializedName("email"     ) var email     : String? = null,
                               @SerializedName("phone"     ) var phone     : String? = null,
                               @SerializedName("gender"    ) var gender    : String? = null,
                               @SerializedName("Birthday"  ) var Birthday  : String? = null)