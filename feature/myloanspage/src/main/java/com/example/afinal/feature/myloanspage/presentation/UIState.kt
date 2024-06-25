package com.example.afinal.feature.myloanspage.presentation

import com.example.afinal.shared.loans.domain.entities.Loan

sealed interface UIState {
    data object Loading : UIState
    data class Content(
        val data: List<Loan>,
    ) : UIState

    data class Error(
        val error: String,
        val code: Int
    )
}