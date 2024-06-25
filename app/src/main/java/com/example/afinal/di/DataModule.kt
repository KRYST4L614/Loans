package com.example.afinal.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.afinal.feature.auth.data.AuthApiService
import com.example.afinal.feature.auth.data.AuthRepositoryImpl
import com.example.afinal.feature.auth.data.AuthTokenRepositoryImpl
import com.example.afinal.feature.auth.domain.repositories.AuthRepository
import com.example.afinal.feature.auth.domain.repositories.AuthTokenRepository
import com.example.afinal.feature.homepage.HomePageRouter
import com.example.afinal.feature.loandetails.data.LoanDetailsApiService
import com.example.afinal.feature.loandetails.data.LoanDetailsRepositoryImpl
import com.example.afinal.feature.loandetails.domain.LoanDetailsRepository
import com.example.afinal.shared.loans.data.LoansApiService
import com.example.afinal.shared.loans.data.LoansRepositoryImpl
import com.example.afinal.shared.loans.domain.LoansRepository
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
interface DataModule {

    companion object {
        private const val PREFERENCES_FILENAME = "Preferences"
        private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        @Provides
        fun provideRetrofit(
            authTokenRepository: AuthTokenRepository,
            router: HomePageRouter
        ): Retrofit {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val authInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                authTokenRepository.getToken()?.let {
                    requestBuilder.addHeader("Authorization", it)
                }
                val response = chain.proceed(requestBuilder.build())
                if (response.code == 403) {
                    router.openAuth()
                }
                return@Interceptor response
            }

            val client = OkHttpClient.Builder()
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .addInterceptor(authInterceptor)
                .build()
            return Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .baseUrl("https://shift-loan-app.exodar.heartlessguy.pro/")
                .build()
        }

        @Provides
        fun provideEncryptedSharedPreferences(context: Context): EncryptedSharedPreferences =
            EncryptedSharedPreferences.create(
                PREFERENCES_FILENAME,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ) as EncryptedSharedPreferences

        @Provides
        fun provideAuthRepository(service: AuthApiService): AuthRepository =
            AuthRepositoryImpl(service, Dispatchers.IO)

        @Provides
        fun provideService(retrofit: Retrofit): AuthApiService {
            return retrofit.create(AuthApiService::class.java)
        }

        @Provides
        fun provideLoansApiService(retrofit: Retrofit): LoansApiService =
            retrofit.create(LoansApiService::class.java)

        @Provides
        fun provideLoansRepository(service: LoansApiService): LoansRepository =
            LoansRepositoryImpl(service, Dispatchers.IO)

        @Provides
        fun provideLoanDetailsApiService(retrofit: Retrofit): LoanDetailsApiService =
            retrofit.create(LoanDetailsApiService::class.java)

        @Provides
        fun provideLoanDetailsRepository(service: LoanDetailsApiService): LoanDetailsRepository =
            LoanDetailsRepositoryImpl(service, Dispatchers.IO)
    }

    @Binds
    fun bindAuthTokenRepositoryImpl(impl: AuthTokenRepositoryImpl): AuthTokenRepository

}