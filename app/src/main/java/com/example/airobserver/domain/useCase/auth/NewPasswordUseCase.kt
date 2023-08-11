package com.example.airobserver.domain.useCase.auth

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewPasswordUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String,password:String): Flow<ApiResponseStates<BaseResponse<String>>> {
        return repository.newPassword(email, password)
    }
}