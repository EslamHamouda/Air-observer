package com.example.airobserver.domain.useCase.auth

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.GetProfileResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String): Flow<ApiResponseStates<BaseResponse<GetProfileResponse>>> {
        return repository.getProfile(email)
    }
}