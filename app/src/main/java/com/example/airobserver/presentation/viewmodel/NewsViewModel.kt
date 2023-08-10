package com.example.airobserver.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.useCase.GetAqiOfDayUseCase
import com.example.airobserver.useCase.GetNewsUseCase
import com.example.airobserver.utils.ApiResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _newsResponse: MutableStateFlow<ApiResponseStates<NewsResponse>> =
        MutableStateFlow(ApiResponseStates.Success(null))
    val newsResponse: StateFlow<ApiResponseStates<NewsResponse>>
        get() = _newsResponse

    fun getNews() {
        viewModelScope.launch {
           getNewsUseCase().collectLatest {
                _newsResponse.value =
                    it
            }
        }
    }
}