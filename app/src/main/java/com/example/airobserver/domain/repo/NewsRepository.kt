package com.example.airobserver.domain.repo

import com.example.airobserver.data.APIServicesNews
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val services: APIServicesNews
) {
    suspend fun getNews() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.getNews("air pollution","c9402dc3e3d1417f8a8759bb2a909b46")
            }
        }
}