package com.example.afinal.feature.auth.domain.usecases

import com.example.afinal.feature.auth.domain.AuthRepository
import com.example.afinal.feature.auth.domain.entities.LoginUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(loginUser: LoginUser) = repository.login(loginUser)
}