package com.example.airobserver.domain.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
  var email : String? = null,
    var password  : String? = null
)
