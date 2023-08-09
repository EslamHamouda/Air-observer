package com.example.airobserver.di

import com.example.airobserver.data.remote.APIServices
import com.example.airobserver.data.remote.APIServicesNews
import com.example.airobserver.data.repo.AuthRepositoryImpl
import com.example.airobserver.data.repo.HomeRepositoryImpl
import com.example.airobserver.data.repo.NewsRepositoryImpl
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.domain.repo.NewsRepository
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

    @Provides
    fun provideAuthRepository(services: APIServices): AuthRepository {
        return AuthRepositoryImpl(services)
    }

    @Provides
    fun provideHomeRepository(services: APIServices): HomeRepository {
        return HomeRepositoryImpl(services)
    }

    @Provides
    fun provideNewsRepository(services: APIServicesNews): NewsRepository {
        return NewsRepositoryImpl(services)
    }
}