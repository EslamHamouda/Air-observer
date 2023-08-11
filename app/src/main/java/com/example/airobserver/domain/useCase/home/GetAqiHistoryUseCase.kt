package com.example.airobserver.domain.useCase.home

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.AqiHistoryResponse
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAqiHistoryUseCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke(month: Int): Flow<ApiResponseStates<BaseResponse<AqiHistoryResponse>>> {
        return repository.aqiHistory(month)
    }
}