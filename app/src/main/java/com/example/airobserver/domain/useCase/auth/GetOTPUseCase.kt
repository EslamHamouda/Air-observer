package com.example.airobserver.domain.useCase.auth

import com.example.airobserver.R
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.isValidEmail
import com.example.airobserver.utils.isValidPassword
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOTPUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String): ApiResponseStates<BaseResponse<String>> {
        val validationFailure = mutableMapOf<String, Boolean>()
        return try {
            if (!isValidEmail(email)) {
                validationFailure["isValidEmail"]=false
                ApiResponseStates.Failure.Validation(validationFailure)
            } else {
                ApiResponseStates.Success(repository.getOTP(email))
            }
        } catch (throwable: Throwable) {
            ApiResponseStates.Failure.Network(throwable)
        }
    }
}