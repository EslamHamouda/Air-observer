package com.example.airobserver.data.repo

import com.example.airobserver.data.remote.APIServicesNews
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val services: APIServicesNews
):NewsRepository {
    override suspend fun getNews() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.getNews("air pollution","c9402dc3e3d1417f8a8759bb2a909b46")
            }
        }
}