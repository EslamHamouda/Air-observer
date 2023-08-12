package com.example.airobserver.domain.useCase.auth

import com.example.airobserver.R
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.isValidEmail
import com.example.airobserver.utils.isValidPassword
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String,password:String): ApiResponseStates<BaseResponse<LoginResponse>> {
        return try {
            val validationFailure = mutableMapOf<String, String>()
            if (!isValidEmail(email)){
                validationFailure["isValidEmail"]=R.string.enter_a_valid_email.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
            }
            else if(!isValidPassword(password)){
                validationFailure["isValidPassword"]=R.string.password_should_be_8_or_more.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
            }
            else{
                ApiResponseStates.Success(repository.login(email, password))
            }
        }catch (throwable:Throwable){
            ApiResponseStates.Failure(throwable)
        }
    }
}