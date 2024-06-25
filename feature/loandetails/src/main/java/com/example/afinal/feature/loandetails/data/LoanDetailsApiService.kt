package com.example.afinal.feature.loandetails.data

import com.example.afinal.shared.loans.domain.entities.Loan
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LoanDetailsApiService {
    @GET("loans/{id}")
    fun getLoanDetails(@Path("id") id: Int): Call<Loan>
}