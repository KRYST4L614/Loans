package com.example.afinal.feature.auth.domain.usecases

import com.example.afinal.shared.loans.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository
) {
    operator fun invoke(token: String?) = authTokenRepository.saveToken(token)
}