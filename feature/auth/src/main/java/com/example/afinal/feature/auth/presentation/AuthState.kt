package com.example.afinal.feature.auth.presentation

sealed interface AuthState {
    data class Error(val errorMessage: String) : AuthState
    data object Loading : AuthState
    data class Content(
        val nameErrorMessage: String? = null,
        val passwordErrorMessage: String? = null,
        val repeatedPasswordErrorMessage: String? = null
    ) : AuthState
}