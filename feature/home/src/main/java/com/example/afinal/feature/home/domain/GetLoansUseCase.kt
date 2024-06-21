package com.example.afinal.feature.home.domain

import javax.inject.Inject

class GetLoansUseCase @Inject constructor(
    private val repository: LoansRepository
) {
    suspend operator fun invoke() = repository.getLoans()
}