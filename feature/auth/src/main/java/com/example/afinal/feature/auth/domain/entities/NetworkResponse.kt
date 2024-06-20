package com.example.afinal.feature.auth.domain.entities

sealed interface NetworkResponse<out T> {
    data class Success<T>(val content: T) : NetworkResponse<T>
    data class Error(val t: Throwable) : NetworkResponse<Nothing>
}