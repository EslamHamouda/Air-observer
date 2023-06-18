package com.example.airobserver.di

import com.example.airobserver.data.APIServices
import com.example.airobserver.data.APIServicesNews
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://airobserver5-001-site1.btempurl.com"
    @Provides
    @Singleton
    @Named("RetrofitObject")
    fun provideRetrofit(): Retrofit {
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