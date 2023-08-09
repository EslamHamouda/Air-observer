package com.example.airobserver.data.remote

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.*
import retrofit2.http.*

interface APIServices {
    @POST("apiregisteracademy/userinfo.php")
    @FormUrlEncoded
    suspend fun login(@Field("Email") email:String,@Field("Password") password:String): BaseResponse<LoginResponse>

    @POST("apiregisteracademy/SignUp.php")
    @FormUrlEncoded
    suspend fun register(@Field("FirstName") fname:String,
                         @Field("LastName") lname:String,
                         @Field("Email") email:String,
                         @Field("Phone") phone:String,
                         @Field("Password") password:String,
                         @Field("Birthday") birthday:String,
                         @Field("Gender") gender:String
    ): BaseResponse<String>

    @POST("apiregisteracademy/Forgetten.php")
    @FormUrlEncoded
    suspend fun getOTP(@Field("Email") email:String): BaseResponse<String>

    @POST("apiregisteracademy/OTP.php")
    @FormUrlEncoded
    suspend fun checkOTP(@Field("Email") email:String, @Field("VerifyCode") otp:Int): BaseResponse<String>

    @POST("apiregisteracademy/ChangePass.php")
    @FormUrlEncoded
    suspend fun newPassword(@Field("Email") email:String, @Field("Password") password:String): BaseResponse<String>

    @POST("apiregisteracademy/datainfo.php")
    @FormUrlEncoded
    suspend fun getProfile(@Field("Email") email:String): BaseResponse<GetProfileResponse>

    @POST("apiregisteracademy/newda.php")
    @FormUrlEncoded
    suspend fun updateProfile(@Field("FirstName") fname:String,
                              @Field("LastName") lname:String,
                              @Field("Email") email:String,
                              @Field("Phone") phone:String,
                              @Field("Gender") gender:String,
                              @Field("Birthday") birthday:String): BaseResponse<String>

    @GET("APIofSensors/apiDummyData.php")
    suspend fun aqiHistory(@Query("month") month:Int): BaseResponse<AqiHistoryResponse>

    @GET("APIofSensors/aqiGraphHistory.php")
    suspend fun aqiGraphHistory(): BaseResponse<ArrayList<AqiGraphHistoryResponse>>

    @GET("APIofSensors/aqiOfDay.php")
    suspend fun aqiOfDay(): BaseResponse<AqiOfDayResponse>

    @GET("APIofSensors/aqiGraphLastHours.php")
    suspend fun aqiGraphLastHours(): BaseResponse<ArrayList<AqiGraphLastHoursResponse>>

}