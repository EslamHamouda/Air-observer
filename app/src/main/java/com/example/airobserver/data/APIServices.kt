package com.example.airobserver.data

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.request.LoginRequest
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.model.response.NewsResponse
import retrofit2.http.*

interface APIServices {
    @POST("userinfo.php")
    @FormUrlEncoded
    suspend fun login(@FieldMap body:Map<String,String>): BaseResponse<LoginResponse>

}