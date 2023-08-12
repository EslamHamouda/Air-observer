package com.example.airobserver.domain.useCase.auth

import com.example.airobserver.R
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.GetProfileResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.isValidEmail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String): ApiResponseStates<BaseResponse<GetProfileResponse>> {
        return try {
            ApiResponseStates.Success(repository.getProfile(email))
        } catch (throwable: Throwable) {
            ApiResponseStates.Failure(throwable)
        }
    }
}