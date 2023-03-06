package com.example.airobserver.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.model.response.RegisterResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginResponse: MutableStateFlow<ApiResponseStates<BaseResponse<LoginResponse>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val loginResponse: StateFlow<ApiResponseStates<BaseResponse<LoginResponse>>>
        get() = _loginResponse

    fun login(email:String,password:String) {
        viewModelScope.launch {
            repository.login(email, password).collectLatest {
                _loginResponse.value =
                    it
            }
        }
    }

    private val _registerResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val registerResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _registerResponse

    fun register(fname:String,
                 lname:String,
                 email:String,
                 phone:String,
                 password:String,
                 birthday:String,
                 gender:String) {
        viewModelScope.launch {
            repository.register(fname, lname, email, phone, password, birthday, gender).collectLatest {
                _registerResponse.value =
                    it as ApiResponseStates<BaseResponse<String>>
            }
        }
    }

    private val _getOTPResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val getOTPResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _getOTPResponse

    fun getOTP(email:String) {
        viewModelScope.launch {
            repository.getOTP(email).collectLatest {
                _getOTPResponse.value =
                    it
            }
        }
    }

    private val _checkOTPResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val checkOTPResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _checkOTPResponse

    fun checkOTP(email:String,otp:Int) {
        viewModelScope.launch {
            repository.checkOTP(email, otp).collectLatest {
                _checkOTPResponse.value =
                    it as ApiResponseStates<BaseResponse<String>>
            }
        }
    }

    private val _newPasswordResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val newPasswordResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _newPasswordResponse

    fun newPassword(email:String,password:String) {
        viewModelScope.launch {
            repository.newPassword(email, password).collectLatest {
                _newPasswordResponse.value =
                    it as ApiResponseStates<BaseResponse<String>>
            }
        }
    }
}