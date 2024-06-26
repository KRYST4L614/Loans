package com.example.afinal.feature.myloanspage.presentation

import com.example.afinal.shared.loans.domain.entities.Loan

sealed interface MyLoansPageState {
    data object Loading : MyLoansPageState
    data class Content(
        val data: List<Loan>,
    ) : MyLoansPageState

    data class Error(
        val errorMessage: String
    ) : MyLoansPageState
}