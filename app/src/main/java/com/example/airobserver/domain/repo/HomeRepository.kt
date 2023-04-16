package com.example.airobserver.domain.repo

import com.example.airobserver.data.APIServices
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Month
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val services: APIServices
) {
    suspend fun aqiHistory(month: Int) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiHistory(month)
            }
        }

    suspend fun aqiGraphHistory() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiGraphHistory()
            }
        }

    suspend fun aqiOfDay() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiOfDay()
            }
        }

    suspend fun aqiGraphLastHours() =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.aqiGraphLastHours()
            }
        }
}