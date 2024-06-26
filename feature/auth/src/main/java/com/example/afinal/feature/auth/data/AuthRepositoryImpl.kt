package com.example.afinal.feature.auth.data

import com.example.afinal.feature.auth.domain.entities.LoggedUser
import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.repositories.AuthRepository
import com.example.afinal.util.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthApiService,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun login(loginUser: LoginUser): NetworkResponse<String> =
        withContext(dispatcher) {
            var responseCode: Int? = null
            try {
                val response = service.login(loginUser).execute()
                responseCode = response.code()
                NetworkResponse.Success(
                    response.body() ?: throw IllegalStateException("Illegal response")
                )
            } catch (e: Exception) {
                NetworkResponse.Error(e, responseCode)
            }
        }

    override suspend fun registration(loginUser: LoginUser): NetworkResponse<LoggedUser> =
        withContext(dispatcher) {
            var responseCode: Int? = null
            try {
                val response = service.registration(loginUser).execute()
                responseCode = response.code()
                NetworkResponse.Success(
                    response.body() ?: throw IllegalStateException("Illegal response")
                )
            } catch (e: Exception) {
                NetworkResponse.Error(e, responseCode)
            }
        }
}