package com.example.airobserver.domain.repo

import com.example.airobserver.data.APIServices
import com.example.airobserver.data.APIServicesNews
import com.example.airobserver.domain.model.request.LoginRequest
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val services: APIServices
) {
    suspend fun login(body:Map<String,String>) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.login(body)
            }
        }
}