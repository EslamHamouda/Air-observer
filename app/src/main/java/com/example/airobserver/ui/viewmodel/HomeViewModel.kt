package com.example.airobserver.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.AqiHistory
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.utils.ApiResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _aqiHistoryResponse: MutableStateFlow<ApiResponseStates<BaseResponse<ArrayList<AqiHistory>>>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val aqiHistoryResponse: StateFlow<ApiResponseStates<BaseResponse<ArrayList<AqiHistory>>>>
        get() = _aqiHistoryResponse

    fun aqiHistory() {
        viewModelScope.launch {
            repository.aqiHistory().collectLatest {
                _aqiHistoryResponse.value =
                    it as ApiResponseStates<BaseResponse<ArrayList<AqiHistory>>>
            }
        }
    }

}