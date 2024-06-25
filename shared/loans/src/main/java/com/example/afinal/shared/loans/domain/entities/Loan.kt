package com.example.afinal.shared.loans.domain.entities

import java.util.Date

enum class Status {
    APPROVED,
    REGISTERED,
    REJECTED
}

data class Loan(
    val amount: Double,
    val date: Date,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: Status
)
