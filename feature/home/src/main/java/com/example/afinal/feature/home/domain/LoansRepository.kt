package com.example.afinal.feature.home.domain

import com.example.afinal.feature.home.domain.entitites.Loan
import com.example.afinal.feature.home.domain.entitites.Loans
import com.example.afinal.util.NetworkResponse

interface LoansRepository {
    suspend fun getLoans(): NetworkResponse<List<Loan>>
}