package com.example.afinal.feature.auth.domain.usecases

import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.repositories.AuthRepository
import com.example.afinal.feature.auth.domain.repositories.AuthTokenRepository
import com.example.afinal.util.NetworkResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authTokenRepository: AuthTokenRepository
) {
    suspend operator fun invoke(loginUser: LoginUser): NetworkResponse<String> {
        authTokenRepository.saveToken(null)
        val response = authRepository.login(loginUser)
        if (response is NetworkResponse.Success) {
            authTokenRepository.saveToken(response.content)
        } else {
            authTokenRepository.saveToken(null)
        }
        return response
    }
}