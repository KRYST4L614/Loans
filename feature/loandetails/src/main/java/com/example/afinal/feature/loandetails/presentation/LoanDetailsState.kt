package com.example.afinal.feature.loandetails.presentation

import com.example.afinal.shared.loans.domain.entities.Loan

sealed interface LoanDetailsState {
    data class Content(
        val data: Loan
    ) : LoanDetailsState

    data object Loading : LoanDetailsState

    data class Error(
        val errorMessage: String
    ) : LoanDetailsState
}