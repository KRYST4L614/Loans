package com.example.afinal.feature.requestloan.presentation

sealed interface RequestLoanState {
    data class Error(
        val errorMessage: String
    ) : RequestLoanState

    data object Loading : RequestLoanState
    data class Content(
        val phoneNumberError: String? = null,
        val firstNameError: String? = null,
        val lastNameError: String? = null
    ) : RequestLoanState
}