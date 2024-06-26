package com.example.afinal.feature.homepage.domain

import com.example.afinal.shared.loans.domain.entities.LoanConditions
import com.example.afinal.util.NetworkResponse

interface LoanConditionsRepository {
    suspend fun getLoanConditions(): NetworkResponse<LoanConditions>
}