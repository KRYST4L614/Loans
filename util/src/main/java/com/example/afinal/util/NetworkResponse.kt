package com.example.afinal.util

sealed interface NetworkResponse<out T> {
    data class Success<T>(val content: T) : NetworkResponse<T>
    data class Error(val e: Exception, val code: Int?) : NetworkResponse<Nothing>
}