package com.example.afinal.shared.loans.domain

import com.example.afinal.shared.loans.domain.repositories.LoansRepository
import javax.inject.Inject

class GetLoansUseCase @Inject constructor(
    private val repository: LoansRepository
) {
    suspend operator fun invoke() = repository.getLoans()
}