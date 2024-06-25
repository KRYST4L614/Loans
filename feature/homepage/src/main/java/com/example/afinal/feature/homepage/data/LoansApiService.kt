package com.example.afinal.feature.homepage.data

import com.example.afinal.feature.homepage.domain.entities.Loan
import retrofit2.Call
import retrofit2.http.GET

interface LoansApiService {
    @GET("loans/all")
    fun getLoans(): Call<List<Loan>>
}