package com.example.airobserver.domain.repo

import com.example.airobserver.data.APIServices
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Field
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val services: APIServices
) {
    suspend fun login(email:String,password:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.login(email, password)
            }
        }

    suspend fun register(fname:String,
                         lname:String,
                         email:String,
                         phone:String,
                         password:String,
                         birthday:String,
                         gender:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.register(fname, lname, email, phone, password, birthday, gender)
            }
        }
}