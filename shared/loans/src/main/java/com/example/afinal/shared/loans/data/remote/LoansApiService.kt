package com.example.afinal.shared.loans.data.remote

import com.example.afinal.shared.loans.domain.entities.Loan
import retrofit2.Call
import retrofit2.http.GET

interface LoansApiService {
    @GET("loans/all")
    fun getLoans(): Call<List<Loan>>
}