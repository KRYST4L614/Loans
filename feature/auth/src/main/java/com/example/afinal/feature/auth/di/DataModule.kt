package com.example.afinal.feature.auth.di

import com.example.afinal.feature.auth.data.AuthApiService
import com.example.afinal.feature.auth.data.AuthRepositoryImpl
import com.example.afinal.feature.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
class DataModule {
    @Provides
    fun provideAuthRepository(service: AuthApiService): AuthRepository =
        AuthRepositoryImpl(service, Dispatchers.IO)

    @Provides
    fun provideService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}