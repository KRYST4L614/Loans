package com.example.afinal.feature.loandetails.domain

import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.util.NetworkResponse

interface LoanDetailsRepository {
    suspend fun getLoanDetails(id: Int): NetworkResponse<Loan>
}