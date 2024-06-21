package com.example.afinal.feature.auth.data

import androidx.security.crypto.EncryptedSharedPreferences
import com.example.afinal.feature.auth.domain.repositories.AuthTokenRepository

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