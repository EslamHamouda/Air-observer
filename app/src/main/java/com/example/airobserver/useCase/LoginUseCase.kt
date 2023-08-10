package com.example.airobserver.useCase

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String,password:String): Flow<ApiResponseStates<BaseResponse<LoginResponse>>> {
        return repository.login(email, password)
    }
}