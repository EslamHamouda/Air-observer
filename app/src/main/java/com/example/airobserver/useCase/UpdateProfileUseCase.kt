package com.example.airobserver.useCase

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(fname:String,
                                lname:String,
                                email:String,
                                phone:String,
                                gender:String,
                                birthdate:String): Flow<ApiResponseStates<BaseResponse<String>>> {
        return repository.updateProfile(fname, lname, email, phone, gender, birthdate)
    }
}