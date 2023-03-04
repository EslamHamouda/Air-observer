package com.example.airobserver.di

import android.content.SharedPreferences
import com.example.airobserver.data.APIServicesNews
import com.example.airobserver.data.APIServices
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://newsapi.org/v2/everything?q=air%20pollution&apiKey=c9402dc3e3d1417f8a8759bb2a909b46"
    @Provides
    @Singleton
    @Named("RetrofitObject")
    fun provideRetrofit(pref: SharedPreferences): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .build()
            )
        })
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun retrofitServices(
        @Named("RetrofitObject")
        retrofit: Retrofit
    ): APIServices =
        retrofit.create(APIServices::class.java)



    private const val apiKey = "c9402dc3e3d1417f8a8759bb2a909b46"
    private const val query = "air pollution"
    private const val BASE_URL_NEWS = "https://newsapi.org/v2/"
    @Provides
    @Singleton
    @Named("RetrofitObjectNews")
    fun provideRetrofitNews(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .build()
            )
        })
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(BASE_URL_NEWS)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun retrofitServicesNews(
        @Named("RetrofitObjectNews")
        retrofit: Retrofit
    ): APIServicesNews =
        retrofit.create(APIServicesNews::class.java)
}