package com.example.afinal.shared.loans.domain

import com.example.afinal.util.NetworkResponse
import com.example.afinal.shared.loans.domain.entities.Loan

interface LoansRepository {
    suspend fun getLoans(): NetworkResponse<List<Loan>>
}