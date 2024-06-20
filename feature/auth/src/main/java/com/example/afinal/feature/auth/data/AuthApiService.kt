package com.example.afinal.feature.auth.data

import com.example.afinal.feature.auth.domain.entities.LoginUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("login")
    fun login(
        @Body requestBody: LoginUser
    ): Call<String>
}