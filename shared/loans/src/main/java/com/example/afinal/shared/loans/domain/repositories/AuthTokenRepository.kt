package com.example.afinal.shared.loans.domain.repositories

interface AuthTokenRepository {
    fun saveToken(token: String?)
    fun getToken(): String?
}