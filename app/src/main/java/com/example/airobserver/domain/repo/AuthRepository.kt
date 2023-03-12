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
                         birthdate: String,
                         gender:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.register(fname, lname, email, phone, password,birthdate, gender)
            }
        }

    suspend fun getOTP(email:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.getOTP(email)
            }
        }

    suspend fun checkOTP(email:String,otp:Int) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.checkOTP(email, otp)
            }
        }

    suspend fun newPassword(email:String,password:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.newPassword(email, password)
            }
        }

    suspend fun getProfile(email:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.getProfile(email)
            }
        }

    suspend fun updateProfile(fname:String,
                              lname:String,
                              email:String,
                              phone:String,
                              password:String,
                              gender:String,
                              birthdate:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.updateProfile(fname,lname,email,phone,password,gender,birthdate)
            }
        }
}