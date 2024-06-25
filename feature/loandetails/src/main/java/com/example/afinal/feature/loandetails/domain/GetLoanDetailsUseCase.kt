package com.example.afinal.feature.loandetails.domain

import javax.inject.Inject

class GetLoanDetailsUseCase @Inject constructor(
    private val repository: LoanDetailsRepository
) {
    suspend operator fun invoke(id: Int) = repository.getLoanDetails(id)
}