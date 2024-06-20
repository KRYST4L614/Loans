package com.example.afinal.feature.auth.presentation

sealed interface AuthState {
    data class Error(val error: String) : AuthState
    data object Success : AuthState
    data object Loading : AuthState
}