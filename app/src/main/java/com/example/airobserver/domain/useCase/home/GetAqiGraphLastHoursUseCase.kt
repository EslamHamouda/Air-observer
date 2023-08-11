package com.example.airobserver.domain.useCase.home

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.AqiGraphLastHoursResponse
import com.example.airobserver.domain.model.response.AqiHistoryResponse
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAqiGraphLastHoursUseCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke(): Flow<ApiResponseStates<BaseResponse<ArrayList<AqiGraphLastHoursResponse>>>> {
        return repository.aqiGraphLastHours()
    }
}