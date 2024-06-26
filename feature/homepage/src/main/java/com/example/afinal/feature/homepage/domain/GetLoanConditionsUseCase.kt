package com.example.afinal.feature.homepage.domain

import javax.inject.Inject

class GetLoanConditionsUseCase @Inject constructor(
    private val repository: LoanConditionsRepository
) {
    suspend operator fun invoke() = repository.getLoanConditions()
}