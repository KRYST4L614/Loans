package com.example.afinal.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.afinal.BuildConfig
import com.example.afinal.feature.auth.data.AuthApiService
import com.example.afinal.feature.auth.data.AuthRepositoryImpl
import com.example.afinal.feature.auth.domain.repositories.AuthRepository
import com.example.afinal.feature.homepage.HomePageRouter
import com.example.afinal.feature.homepage.data.LoansConditionRepositoryImpl
import com.example.afinal.feature.homepage.data.remote.LoanConditionsApiService
import com.example.afinal.feature.homepage.domain.LoanConditionsRepository
import com.example.afinal.feature.loandetails.data.LoanDetailsApiService
import com.example.afinal.feature.loandetails.data.LoanDetailsRepositoryImpl
import com.example.afinal.feature.loandetails.domain.LoanDetailsRepository
import com.example.afinal.feature.requestloan.data.RequestLoanApiService
import com.example.afinal.feature.requestloan.data.RequestLoanRepositoryImpl
import com.example.afinal.feature.requestloan.domain.RequestLoanRepository
import com.example.afinal.feature.splash.domain.GetTokenUseCase
import com.example.afinal.shared.loans.data.remote.LoansApiService
import com.example.afinal.shared.loans.data.repositories.AuthTokenRepositoryImpl
import com.example.afinal.shared.loans.data.repositories.LoansRepositoryImpl
import com.example.afinal.shared.loans.domain.repositories.AuthTokenRepository
import com.example.afinal.shared.loans.domain.repositories.LoansRepository
import com.example.afinal.shared.resourceprovider.ResourceProvider
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
interface DataModule {

    companion object {
        private const val PREFERENCES_FILENAME = "Preferences"
        private const val BASE_URL: String = BuildConfig.BASE_URL
        private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        @Provides
        fun provideRetrofit(
            getTokenUseCase: GetTokenUseCase,
            router: HomePageRouter
        ): Retrofit {

            val authInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                getTokenUseCase()?.let {
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
                .baseUrl(BASE_URL)
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

        @Singleton
        @Provides
        fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
            return retrofit.create(AuthApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideLoansApiService(retrofit: Retrofit): LoansApiService =
            retrofit.create(LoansApiService::class.java)

        @Provides
        fun provideLoansRepository(service: LoansApiService): LoansRepository =
            LoansRepositoryImpl(service, Dispatchers.IO)

        @Singleton
        @Provides
        fun provideLoanDetailsApiService(retrofit: Retrofit): LoanDetailsApiService =
            retrofit.create(LoanDetailsApiService::class.java)

        @Provides
        fun provideLoanDetailsRepository(service: LoanDetailsApiService): LoanDetailsRepository =
            LoanDetailsRepositoryImpl(service, Dispatchers.IO)

        @Singleton
        @Provides
        fun provideRequestLoanApiService(retrofit: Retrofit): RequestLoanApiService =
            retrofit.create(RequestLoanApiService::class.java)

        @Provides
        fun provideRequestLoanRepository(service: RequestLoanApiService): RequestLoanRepository =
            RequestLoanRepositoryImpl(service, Dispatchers.IO)

        @Singleton
        @Provides
        fun provideLoanConditionsApiService(retrofit: Retrofit): LoanConditionsApiService =
            retrofit.create(LoanConditionsApiService::class.java)

        @Provides
        fun provideLoanConditionsRepository(service: LoanConditionsApiService):
                LoanConditionsRepository = LoansConditionRepositoryImpl(service, Dispatchers.IO)

        @Singleton
        @Provides
        fun provideResourceProvider(context: Context) = ResourceProvider(context)
    }

    @Binds
    fun bindAuthTokenRepositoryImpl(impl: AuthTokenRepositoryImpl): AuthTokenRepository

}