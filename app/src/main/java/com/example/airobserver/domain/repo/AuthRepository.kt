package com.example.airobserver.domain.repo

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.GetProfileResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    suspend fun login(email:String,password:String): BaseResponse<LoginResponse>

    suspend fun register(fname:String,
                         lname:String,
                         email:String,
                         phone:String,
                         password:String,
                         birthdate: String,
                         gender:String):  BaseResponse<String>

    suspend fun getOTP(email:String): BaseResponse<String>

    suspend fun checkOTP(email:String,otp:Int): BaseResponse<String>

    suspend fun newPassword(email:String,password:String): BaseResponse<String>

    suspend fun getProfile(email:String): BaseResponse<GetProfileResponse>

    suspend fun updateProfile(fname:String,
                              lname:String,
                              email:String,
                              phone:String,
                              gender:String,
                              birthdate:String): BaseResponse<String>
}