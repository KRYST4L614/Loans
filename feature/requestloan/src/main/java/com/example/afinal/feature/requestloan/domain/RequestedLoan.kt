package com.example.afinal.feature.requestloan.domain

data class RequestedLoan(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)