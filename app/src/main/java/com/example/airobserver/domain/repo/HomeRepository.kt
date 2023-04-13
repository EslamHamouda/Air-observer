package com.example.airobserver.domain.repo

import com.example.airobserver.data.APIServices
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val services: APIServices
) {
    suspend fun aqiHistory() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiHistory()
            }
        }
}