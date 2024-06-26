package com.example.afinal.feature.requestloan.domain

import javax.inject.Inject

class RequestLoanUseCase @Inject constructor(
    private val repository: RequestLoanRepository
) {
    suspend operator fun invoke(requestedLoan: RequestedLoan) =
        repository.requestLoan(requestedLoan)
}