package com.example.afinal.feature.auth.domain.repositories

interface AuthTokenRepository {
    fun saveToken(token: String?)
    fun getToken(): String?
}