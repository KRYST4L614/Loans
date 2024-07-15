package com.example.afinal.shared.loans.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanConditions(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
) : Parcelable