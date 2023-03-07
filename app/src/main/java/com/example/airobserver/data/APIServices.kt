package com.example.airobserver.data

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.GetProfileResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.model.response.RegisterResponse
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
                         @Field("Gender") gender:String
    ): BaseResponse<String>

    @POST("Forgetten.php")
    @FormUrlEncoded
    suspend fun getOTP(@Field("Email") email:String): BaseResponse<String>

    @POST("OTP.php")
    @FormUrlEncoded
    suspend fun checkOTP(@Field("Email") email:String, @Field("VerifyCode") otp:Int): BaseResponse<String>

    @POST("ChangePass.php")
    @FormUrlEncoded
    suspend fun newPassword(@Field("Email") email:String, @Field("Password") password:String): BaseResponse<GetProfileResponse>

    @POST("datainfo.php")
    @FormUrlEncoded
    suspend fun getProfile(@Field("Email") email:String): BaseResponse<GetProfileResponse>

    @POST("newda.php")
    @FormUrlEncoded
    suspend fun updateProfile(@Field("FirstName") fname:String,
                              @Field("LastName") lname:String,
                              @Field("Email") email:String,
                              @Field("Phone") phone:String,
                              @Field("Password") password:String,
                              @Field("Gender") gender:String,
                              @Field("Birthday") birthday:String): BaseResponse<String>


}