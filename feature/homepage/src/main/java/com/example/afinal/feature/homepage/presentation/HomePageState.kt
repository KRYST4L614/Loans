package com.example.afinal.feature.homepage.presentation

import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.LoanConditions

sealed interface HomePageState {
    data object Loading : HomePageState
    data class Content(
        val loans: List<Loan>,
        val conditions: LoanConditions,
    ) : HomePageState

    data class Error(
        val errorMessage: String
    ) : HomePageState
}