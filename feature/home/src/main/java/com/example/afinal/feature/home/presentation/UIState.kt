package com.example.afinal.feature.home.presentation

import com.example.afinal.feature.home.domain.entitites.Loan

sealed interface UIState {
    data object Loading : UIState
    data class Content(
        val data: List<Loan>
    ) : UIState

    data class Error(
        val error: String,
        val code: Int
    )
}