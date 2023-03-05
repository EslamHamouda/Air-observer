package com.example.airobserver.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
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
                    it
            }
        }
    }
}