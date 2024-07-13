package com.example.afinal.shared.loans.domain.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LoanConditions(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
) : Parcelable