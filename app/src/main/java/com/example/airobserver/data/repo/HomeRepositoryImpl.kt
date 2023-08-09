package com.example.airobserver.data.repo

import com.example.airobserver.data.remote.APIServices
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val services: APIServices
):HomeRepository {
    override suspend fun aqiHistory(month: Int) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiHistory(month)
            }
        }

    override suspend fun aqiGraphHistory() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiGraphHistory()
            }
        }

    override suspend fun aqiOfDay() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiOfDay()
            }
        }

    override suspend fun aqiGraphLastHours() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiGraphLastHours()
            }
        }
}