package com.example.afinal.feature.homepage.presentation

import com.example.afinal.feature.homepage.domain.entities.Loan

sealed interface UIState {
    data object Loading : UIState
    data class Content(
        val data: List<Loan>,
        val toolbarTitle: String,
        val position: Int
    ) : UIState

    data class Error(
        val error: String,
        val code: Int
    )
}