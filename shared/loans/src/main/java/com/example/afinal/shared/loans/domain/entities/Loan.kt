package com.example.afinal.shared.loans.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import java.util.Date

enum class Status {
    APPROVED,
    REGISTERED,
    GRANTED,
    REJECTED
}

@Parcelize
data class Loan(
    val amount: Double,
    val date: Date,
    val issueDate: Date,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val status: Status
) : Parcelable
