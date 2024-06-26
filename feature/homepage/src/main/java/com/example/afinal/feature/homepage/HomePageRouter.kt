package com.example.afinal.feature.homepage

import com.example.afinal.shared.loans.domain.entities.Loan
import com.example.afinal.shared.loans.domain.entities.LoanConditions

interface HomePageRouter {
    fun openAuth()
    fun openOnboarding()
    fun openMyLoans()
    fun openLoanDetails(loan: Loan)
    fun openRequestLoan(loanConditions: LoanConditions)
}