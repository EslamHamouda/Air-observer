package com.example.airobserver.domain.useCase.home

import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.AqiOfDayResponse
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.utils.ApiResponseStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(): Flow<ApiResponseStates<NewsResponse>> {
        return repository.getNews()
    }
}