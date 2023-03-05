package com.example.airobserver.data

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import retrofit2.http.*

interface APIServices {
    @POST("userinfo.php")
    @FormUrlEncoded
    suspend fun login(@Field("Email") email:String,@Field("Password") password:String): BaseResponse<LoginResponse>

    @POST("SignUp.php")
    @FormUrlEncoded
    suspend fun register(@Field("FirstName") fname:String,
                         @Field("LastName") lname:String,
                         @Field("Email") email:String,
                         @Field("Phone") phone:String,
                         @Field("Password") password:String,
                         @Field("Birthday") birthday:String,
                         @Field("Gender") gender:String
    ): BaseResponse<String>

}