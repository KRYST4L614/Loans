package com.example.afinal.feature.requestloan.domain

import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.util.NetworkResponse

interface RequestLoanRepository {
    suspend fun requestLoan(requestedLoan: RequestedLoan): NetworkResponse<Loan>
}