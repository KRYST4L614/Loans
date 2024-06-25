package com.example.afinal.feature.myloanspage

import com.example.afinal.shared.loans.domain.entities.Loan

interface MyLoansPageRouter {
    fun close()
    fun openLoanDetails(loan: Loan)
}