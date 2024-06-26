package com.example.afinal.feature.homepage.data

import com.example.afinal.shared.loans.domain.entities.LoanConditions
import retrofit2.Call
import retrofit2.http.GET

interface LoanConditionsApiService {
    @GET("loans/conditions")
    fun getLoansConditions(): Call<LoanConditions>
}