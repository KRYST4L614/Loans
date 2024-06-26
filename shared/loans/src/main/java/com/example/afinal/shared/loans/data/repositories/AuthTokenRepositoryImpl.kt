package com.example.afinal.shared.loans.data.repositories

import androidx.security.crypto.EncryptedSharedPreferences
import com.example.afinal.shared.loans.domain.repositories.AuthTokenRepository

import javax.inject.Inject

class AuthTokenRepositoryImpl @Inject constructor(
    private val source: EncryptedSharedPreferences
) : AuthTokenRepository {
    override fun saveToken(token: String?) {
        source.edit().putString("TOKEN", token).apply()
    }

    override fun getToken(): String? {
        return source.getString("TOKEN", null)
    }
}