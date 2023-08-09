package com.example.airobserver.domain.repo

import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<ApiResponseStates<NewsResponse>>
}