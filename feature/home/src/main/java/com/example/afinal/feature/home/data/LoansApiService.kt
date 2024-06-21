package com.example.afinal.feature.home.data

import com.example.afinal.feature.home.domain.entitites.Loan
import com.example.afinal.feature.home.domain.entitites.Loans
import retrofit2.Call
import retrofit2.http.GET

interface LoansApiService {
    @GET("loans/all")
    fun getLoans(): Call<List<Loan>>
}