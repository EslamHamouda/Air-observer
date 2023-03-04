package com.example.airobserver.data

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.request.LoginRequest
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.model.response.NewsResponse
import retrofit2.http.*

interface APIServices {
    @POST("userinfo.php")
    @FormUrlEncoded
    suspend fun login(@Field("Email") email:String,@Field("Password") password:String): BaseResponse<LoginResponse>

}