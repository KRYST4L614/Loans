package com.example.afinal.feature.auth.data

import com.example.afinal.feature.auth.domain.AuthRepository
import com.example.afinal.feature.auth.domain.entities.LoggedUser
import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.entities.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthApiService,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun login(loginUser: LoginUser): NetworkResponse<String> =
        withContext(dispatcher) {
            try {
                NetworkResponse.Success(
                    service.login(loginUser).execute().body().toString()
                )
            } catch (e: Exception) {
                NetworkResponse.Error(e)
            }
        }

    override suspend fun registration(loginUser: LoginUser): NetworkResponse<LoggedUser> {
        TODO("Not yet implemented")
    }
}