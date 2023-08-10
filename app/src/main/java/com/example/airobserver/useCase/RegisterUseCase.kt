package com.example.airobserver.useCase

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(fname:String,
                                lname:String,
                                email:String,
                                phone:String,
                                password:String,
                                birthdate: String,
                                gender:String): Flow<ApiResponseStates<BaseResponse<String>>> {
        return repository.register(fname, lname, email, phone, password, birthdate, gender)
    }
}