package com.example.afinal.feature.requestloan.data

import com.example.afinal.feature.requestloan.domain.RequestedLoan
import com.example.afinal.shared.loans.domain.entities.Loan
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestLoanApiService {
    @POST("loans/add")
    fun postLoan(@Body requestedLoan: RequestedLoan): Call<Loan>
}