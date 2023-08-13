package com.example.airobserver.domain.useCase.auth

import com.example.airobserver.R
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.isValidEmail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckOTPUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String,otp:Int): ApiResponseStates<BaseResponse<String>> {
        val validationFailure = mutableMapOf<String, Boolean>()
        return try {
            if(otp.toString().length<6){
                validationFailure["isValidOTP"]= false
                ApiResponseStates.Failure.Validation(validationFailure)
            }
            else {
                ApiResponseStates.Success(repository.checkOTP(email, otp))
            }
        } catch (throwable: Throwable) {
            ApiResponseStates.Failure.Network(throwable)
        }
    }
}