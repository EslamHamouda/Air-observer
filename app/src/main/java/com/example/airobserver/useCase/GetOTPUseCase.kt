package com.example.airobserver.useCase

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOTPUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String): Flow<ApiResponseStates<BaseResponse<String>>> {
        return repository.getOTP(email)
    }
}