package com.example.afinal.di

import com.example.afinal.feature.auth.data.AuthApiService
import com.example.afinal.feature.auth.data.AuthRepositoryImpl
import com.example.afinal.feature.auth.domain.AuthRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
class DataModule {
    @Provides
    fun provideRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl("https://shift-loan-app.exodar.heartlessguy.pro/")
            .build()
    }

    @Provides
    fun provideAuthRepository(service: AuthApiService): AuthRepository =
        AuthRepositoryImpl(service, Dispatchers.IO)

    @Provides
    fun provideService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

}