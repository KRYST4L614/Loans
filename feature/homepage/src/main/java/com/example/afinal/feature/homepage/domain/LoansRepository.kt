package com.example.afinal.feature.homepage.domain

import com.example.afinal.util.NetworkResponse
import com.example.afinal.feature.homepage.domain.entities.Loan

interface LoansRepository {
    suspend fun getLoans(): NetworkResponse<List<Loan>>
}