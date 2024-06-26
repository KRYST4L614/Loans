package com.example.afinal.feature.auth.domain.usecases

import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.repositories.AuthRepository
import com.example.afinal.util.NetworkResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(loginUser: LoginUser): NetworkResponse<String> {
        val response = authRepository.login(loginUser)
        return response
    }
}