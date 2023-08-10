package com.example.airobserver.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.AqiGraphHistoryResponse
import com.example.airobserver.domain.model.response.AqiGraphLastHoursResponse
import com.example.airobserver.domain.model.response.AqiHistoryResponse
import com.example.airobserver.domain.model.response.AqiOfDayResponse
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.useCase.GetAqiGraphHistoryUseCase
import com.example.airobserver.useCase.GetAqiGraphLastHoursUseCase
import com.example.airobserver.useCase.GetAqiHistoryUseCase
import com.example.airobserver.useCase.GetAqiOfDayUseCase
import com.example.airobserver.utils.ApiResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAqiHistoryUseCase: GetAqiHistoryUseCase,
    private val getAqiGraphHistoryUseCase: GetAqiGraphHistoryUseCase,
    private val getAqiOfDayUseCase: GetAqiOfDayUseCase,
    private val getAqiGraphLastHoursUseCase: GetAqiGraphLastHoursUseCase

) : ViewModel() {

    private val _aqiHistoryResponse: MutableStateFlow<ApiResponseStates<BaseResponse<AqiHistoryResponse>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val aqiHistoryResponse: StateFlow<ApiResponseStates<BaseResponse<AqiHistoryResponse>>>
        get() = _aqiHistoryResponse

    fun aqiHistory(month: Int) {
        viewModelScope.launch {
            getAqiHistoryUseCase(month).collectLatest {
                _aqiHistoryResponse.value =
                    it
            }
        }
    }

    private val _aqiGraphHistoryResponse: MutableStateFlow<ApiResponseStates<BaseResponse<ArrayList<AqiGraphHistoryResponse>>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val aqiGraphHistoryResponse: StateFlow<ApiResponseStates<BaseResponse<ArrayList<AqiGraphHistoryResponse>>>>
        get() = _aqiGraphHistoryResponse

    fun aqiGraphHistory() {
        viewModelScope.launch {
            getAqiGraphHistoryUseCase().collectLatest {
                _aqiGraphHistoryResponse.value =
                    it as ApiResponseStates<BaseResponse<ArrayList<AqiGraphHistoryResponse>>>
            }
        }
    }

    private val _aqiOfDayResponse: MutableStateFlow<ApiResponseStates<BaseResponse<AqiOfDayResponse>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val aqiOfDayResponse: StateFlow<ApiResponseStates<BaseResponse<AqiOfDayResponse>>>
        get() = _aqiOfDayResponse

    fun aqiOfDay() {
        viewModelScope.launch {
            getAqiOfDayUseCase().collectLatest {
                _aqiOfDayResponse.value =
                    it as ApiResponseStates<BaseResponse<AqiOfDayResponse>>
            }
        }
    }

    private val _aqiGraphLastHoursResponse: MutableStateFlow<ApiResponseStates<BaseResponse<ArrayList<AqiGraphLastHoursResponse>>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val aqiGraphLastHoursResponse: StateFlow<ApiResponseStates<BaseResponse<ArrayList<AqiGraphLastHoursResponse>>>>
        get() = _aqiGraphLastHoursResponse

    fun aqiGraphLastHours() {
        viewModelScope.launch {
            getAqiGraphLastHoursUseCase().collectLatest {
                _aqiGraphLastHoursResponse.value =
                    it as ApiResponseStates<BaseResponse<ArrayList<AqiGraphLastHoursResponse>>>
            }
        }
    }

}