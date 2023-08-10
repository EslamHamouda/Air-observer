package com.example.airobserver.di

import com.example.airobserver.data.remote.APIServices
import com.example.airobserver.data.remote.APIServicesNews
import com.example.airobserver.data.repo.AuthRepositoryImpl
import com.example.airobserver.data.repo.HomeRepositoryImpl
import com.example.airobserver.data.repo.NewsRepositoryImpl
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.domain.repo.HomeRepository
import com.example.airobserver.domain.repo.NewsRepository
import com.example.airobserver.useCase.CheckOTPUseCase
import com.example.airobserver.useCase.GetAqiGraphHistoryUseCase
import com.example.airobserver.useCase.GetAqiGraphLastHoursUseCase
import com.example.airobserver.useCase.GetAqiHistoryUseCase
import com.example.airobserver.useCase.GetAqiOfDayUseCase
import com.example.airobserver.useCase.GetNewsUseCase
import com.example.airobserver.useCase.GetOTPUseCase
import com.example.airobserver.useCase.GetProfileUseCase
import com.example.airobserver.useCase.LoginUseCase
import com.example.airobserver.useCase.NewPasswordUseCase
import com.example.airobserver.useCase.RegisterUseCase
import com.example.airobserver.useCase.UpdateProfileUseCase
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

    @Provides
    fun provideGetAqiHistoryUseCase(repository: HomeRepository): GetAqiHistoryUseCase {
        return GetAqiHistoryUseCase(repository)
    }

    @Provides
    fun provideGetAqiGraphHistoryUseCase(repository: HomeRepository): GetAqiGraphHistoryUseCase {
        return GetAqiGraphHistoryUseCase(repository)
    }

    @Provides
    fun provideGetAqiOfDayUseCase(repository: HomeRepository): GetAqiOfDayUseCase {
        return GetAqiOfDayUseCase(repository)
    }

    @Provides
    fun provideGetAqiGraphLastHoursUseCase(repository: HomeRepository): GetAqiGraphLastHoursUseCase {
        return GetAqiGraphLastHoursUseCase(repository)
    }

    @Provides
    fun provideGetNewsUseCase(repository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCase(repository)
    }

    @Provides
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    fun provideGetOTPUseCase(repository: AuthRepository): GetOTPUseCase {
        return GetOTPUseCase(repository)
    }

    @Provides
    fun provideCheckOTPUseCase(repository: AuthRepository): CheckOTPUseCase {
        return CheckOTPUseCase(repository)
    }

    @Provides
    fun provideNewPasswordUseCase(repository: AuthRepository): NewPasswordUseCase {
        return NewPasswordUseCase(repository)
    }

    @Provides
    fun provideGetProfileUseCase(repository: AuthRepository): GetProfileUseCase {
        return GetProfileUseCase(repository)
    }

    @Provides
    fun provideUpdateProfileUseCase(repository: AuthRepository): UpdateProfileUseCase {
        return UpdateProfileUseCase(repository)
    }
}