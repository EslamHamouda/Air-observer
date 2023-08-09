package com.example.airobserver.domain.repo

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.AqiGraphHistoryResponse
import com.example.airobserver.domain.model.response.AqiGraphLastHoursResponse
import com.example.airobserver.domain.model.response.AqiHistoryResponse
import com.example.airobserver.domain.model.response.AqiOfDayResponse
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun aqiHistory(month: Int): Flow<ApiResponseStates<BaseResponse<AqiHistoryResponse>>>

    suspend fun aqiGraphHistory(): Flow<ApiResponseStates<BaseResponse<ArrayList<AqiGraphHistoryResponse>>>>

    suspend fun aqiOfDay(): Flow<ApiResponseStates<BaseResponse<AqiOfDayResponse>>>

    suspend fun aqiGraphLastHours(): Flow<ApiResponseStates<BaseResponse<ArrayList<AqiGraphLastHoursResponse>>>>
}