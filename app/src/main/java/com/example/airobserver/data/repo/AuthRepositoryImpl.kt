package com.example.airobserver.data.repo

import com.example.airobserver.data.remote.APIServices
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.startApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val services: APIServices
): AuthRepository {
    override suspend fun login(email:String, password:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.login(email, password)
            }
        }

    override suspend fun register(fname:String,
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

    override suspend fun getOTP(email:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.getOTP(email)
            }
        }

    override suspend fun checkOTP(email:String, otp:Int) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.checkOTP(email, otp)
            }
        }

    override suspend fun newPassword(email:String, password:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.newPassword(email, password)
            }
        }

    override suspend fun getProfile(email:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.getProfile(email)
            }
        }

    override suspend fun updateProfile(fname:String,
                                       lname:String,
                                       email:String,
                                       phone:String,
                                       gender:String,
                                       birthdate:String) =
        withContext(Dispatchers.IO) {
            startApiCall {
                services.updateProfile(fname,lname,email,phone,gender,birthdate)
            }
        }
}