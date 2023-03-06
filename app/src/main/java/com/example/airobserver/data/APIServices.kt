package com.example.airobserver.data

import com.example.airobserver.domain.model.response.NewsResponse
import retrofit2.http.*

interface APIServices {
    @POST("userinfo.php")
    fun login(@Field("Gmail") gmail: String, @Field("password") password: String): NewsResponse

}