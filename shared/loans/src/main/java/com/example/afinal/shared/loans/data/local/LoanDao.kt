package com.example.afinal.shared.loans.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.LoanConditions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

@Dao
interface LoanDao {
    @Query("SELECT * FROM Loan")
    fun getAllLoans(): List<Loan>

    @Upsert
    fun upsertLoans(loans: List<Loan>)

    @Query("DELETE FROM Loan")
    fun deleteAllLoans()

    @Query("SELECT * FROM LoanConditions LIMIT 1")
    fun getLoanConditions(): SharedFlow<LoanConditions>

    @Upsert
    fun upsertLoanConditions(loanConditions: LoanConditions)

    @Query("DELETE FROM LoanConditions")
    fun deleteLoanConditions()
}