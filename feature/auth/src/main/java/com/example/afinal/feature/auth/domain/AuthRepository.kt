package com.example.afinal.feature.auth.domain

import com.example.afinal.feature.auth.domain.entities.LoggedUser
import com.example.afinal.feature.auth.domain.entities.LoginUser
import com.example.afinal.feature.auth.domain.entities.NetworkResponse

interface AuthRepository {
    suspend fun login(loginUser: LoginUser): NetworkResponse<String>
    suspend fun registration(loginUser: LoginUser): NetworkResponse<LoggedUser>
}