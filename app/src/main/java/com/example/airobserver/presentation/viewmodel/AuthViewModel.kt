package com.example.airobserver.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.GetProfileResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.useCase.auth.CheckOTPUseCase
import com.example.airobserver.domain.useCase.auth.GetOTPUseCase
import com.example.airobserver.domain.useCase.auth.GetProfileUseCase
import com.example.airobserver.domain.useCase.auth.LoginUseCase
import com.example.airobserver.domain.useCase.auth.NewPasswordUseCase
import com.example.airobserver.domain.useCase.auth.RegisterUseCase
import com.example.airobserver.domain.useCase.auth.UpdateProfileUseCase
import com.example.airobserver.utils.ApiResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getOTPUseCase: GetOTPUseCase,
    private val checkOTPUseCase: CheckOTPUseCase,
    private val newPasswordUseCase: NewPasswordUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _loginResponse: MutableStateFlow<ApiResponseStates<BaseResponse<LoginResponse>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val loginResponse: StateFlow<ApiResponseStates<BaseResponse<LoginResponse>>>
        get() = _loginResponse

    fun login(email:String,password:String) {
        _loginResponse.value=ApiResponseStates.Loading(true)
        viewModelScope.launch {
            when (val result = loginUseCase(email, password)) {
                    is ApiResponseStates.Success -> {
                        _loginResponse.value=ApiResponseStates.Loading(false)
                        _loginResponse.value = ApiResponseStates.Success(result.value)
                    }
                    is ApiResponseStates.Failure -> {
                        _loginResponse.value=ApiResponseStates.Loading(false)
                        _loginResponse.value=when(result){
                            is ApiResponseStates.Failure.Network-> ApiResponseStates.Failure.Network(result.throwable)
                            is ApiResponseStates.Failure.Validation->ApiResponseStates.Failure.Validation(result.message)
                        }
                    }
                else -> {}
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
                 birthdate: String,
                 gender:String) {
        _registerResponse.value=ApiResponseStates.Loading(true)
        viewModelScope.launch {
            when (val result =  registerUseCase(fname, lname, email, phone, password,birthdate, gender)) {
                is ApiResponseStates.Success -> {
                    _registerResponse.value = ApiResponseStates.Loading(false)
                    _registerResponse.value = ApiResponseStates.Success(result.value)
                }

                is ApiResponseStates.Failure -> {
                    _registerResponse.value = ApiResponseStates.Loading(false)
                    _registerResponse.value = when (result) {
                        is ApiResponseStates.Failure.Network -> ApiResponseStates.Failure.Network(
                            result.throwable
                        )

                        is ApiResponseStates.Failure.Validation -> ApiResponseStates.Failure.Validation(
                            result.message
                        )
                    }
                }
                else -> {}
            }
        }
    }

    private val _getOTPResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val getOTPResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _getOTPResponse

    fun getOTP(email:String) {
        _getOTPResponse.value = ApiResponseStates.Loading(true)
        viewModelScope.launch {
            when (val result = getOTPUseCase(email)) {
                is ApiResponseStates.Success -> {
                    _getOTPResponse.value = ApiResponseStates.Loading(false)
                    _getOTPResponse.value =
                        ApiResponseStates.Success(result.value)
                }

                is ApiResponseStates.Failure -> {
                    _getOTPResponse.value = ApiResponseStates.Loading(false)
                    _getOTPResponse.value = when (result) {
                        is ApiResponseStates.Failure.Network -> ApiResponseStates.Failure.Network(
                            result.throwable
                        )

                        is ApiResponseStates.Failure.Validation -> ApiResponseStates.Failure.Validation(
                            result.message
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private val _checkOTPResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val checkOTPResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _checkOTPResponse

    fun checkOTP(email:String,otp:Int) {
        _checkOTPResponse.value = ApiResponseStates.Loading(true)
        viewModelScope.launch {
            when (val result =  checkOTPUseCase(email, otp)) {
                is ApiResponseStates.Success -> {
                    _checkOTPResponse.value = ApiResponseStates.Loading(false)
                    _checkOTPResponse.value = ApiResponseStates.Success(result.value)
                }
                is ApiResponseStates.Failure -> {
                    _checkOTPResponse.value = ApiResponseStates.Loading(false)
                    _checkOTPResponse.value=when(result){
                        is ApiResponseStates.Failure.Network-> ApiResponseStates.Failure.Network(
                            result.throwable
                        )
                        is ApiResponseStates.Failure.Validation->ApiResponseStates.Failure.Validation(
                            result.message
                        )
                    }
                }
                else -> {}
            }
        }
    }

    private val _newPasswordResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val newPasswordResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _newPasswordResponse

    fun newPassword(email:String,password:String, confirm:String) {
        _newPasswordResponse.value = ApiResponseStates.Loading(true)
        viewModelScope.launch {
            when (val result =  newPasswordUseCase(email, password, confirm)) {
                is ApiResponseStates.Success -> {
                    _newPasswordResponse.value = ApiResponseStates.Loading(false)
                    _newPasswordResponse.value = ApiResponseStates.Success(result.value)
                }
                is ApiResponseStates.Failure -> {
                    _newPasswordResponse.value = ApiResponseStates.Loading(false)
                    _newPasswordResponse.value=when(result){
                        is ApiResponseStates.Failure.Network-> ApiResponseStates.Failure.Network(
                            result.throwable
                        )
                        is ApiResponseStates.Failure.Validation->ApiResponseStates.Failure.Validation(
                            result.message
                        )
                    }
                }
                else -> {}
            }
        }
    }

    private val _getProfileResponse: MutableStateFlow<ApiResponseStates<BaseResponse<GetProfileResponse>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val getProfileResponse: StateFlow<ApiResponseStates<BaseResponse<GetProfileResponse>>>
        get() = _getProfileResponse

    fun getProfile(email:String) {
        _getProfileResponse.value = ApiResponseStates.Loading(true)
        viewModelScope.launch {
            when (val result =  getProfileUseCase(email)) {
                is ApiResponseStates.Success -> {
                    _getProfileResponse.value = ApiResponseStates.Loading(false)
                    _getProfileResponse.value = ApiResponseStates.Success(result.value)
                }
                is ApiResponseStates.Failure -> {
                    _getProfileResponse.value = ApiResponseStates.Loading(false)
                    _getProfileResponse.value=when(result){
                        is ApiResponseStates.Failure.Network-> ApiResponseStates.Failure.Network(
                            result.throwable
                        )
                        is ApiResponseStates.Failure.Validation->ApiResponseStates.Failure.Validation(
                            result.message
                        )
                    }
                }
                else -> {}
            }
        }
    }

    private val _updateProfileResponse: MutableStateFlow<ApiResponseStates<BaseResponse<String>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val updateProfileResponse: StateFlow<ApiResponseStates<BaseResponse<String>>>
        get() = _updateProfileResponse

    fun updateProfile(fname:String,
                 lname:String,
                 email:String,
                 phone:String,
                 gender:String,
                 birthdate:String) {
        _updateProfileResponse.value=ApiResponseStates.Loading(true)
        viewModelScope.launch {
            when (val result =  updateProfileUseCase(fname, lname, email, phone, gender, birthdate)) {
                is ApiResponseStates.Success -> {
                    _updateProfileResponse.value=ApiResponseStates.Loading(false)
                    _updateProfileResponse.value = ApiResponseStates.Success(result.value)
                }
                is ApiResponseStates.Failure -> {
                    _updateProfileResponse.value=ApiResponseStates.Loading(false)
                    _updateProfileResponse.value=when(result){
                        is ApiResponseStates.Failure.Network-> ApiResponseStates.Failure.Network(
                            result.throwable
                        )
                        is ApiResponseStates.Failure.Validation->ApiResponseStates.Failure.Validation(
                            result.message
                        )
                    }
                }
                else -> {}
            }
        }
    }
}