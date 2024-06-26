package com.example.afinal.shared.loans.domain.repositories

import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.util.NetworkResponse

interface LoansRepository {
    suspend fun getLoans(): NetworkResponse<List<Loan>>
}