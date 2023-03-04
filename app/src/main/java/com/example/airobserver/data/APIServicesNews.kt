package com.example.airobserver.data

import com.example.airobserver.domain.model.response.NewsResponse
import retrofit2.http.*

interface APIServicesNews {
    @GET("everything")
    suspend fun getNews(@Query("q") query: String, @Query("apiKey") apiKey: String): NewsResponse
}