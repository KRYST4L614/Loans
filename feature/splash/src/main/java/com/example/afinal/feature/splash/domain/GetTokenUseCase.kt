package com.example.afinal.feature.splash.domain

import com.example.afinal.shared.loans.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: AuthTokenRepository
) {
    operator fun invoke() = repository.getToken()
}