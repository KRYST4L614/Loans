package com.example.afinal.feature.homepage

import com.example.afinal.shared.loans.domain.entities.Loan

interface HomePageRouter {
    fun openAuth()
    fun openOnboarding()
    fun openMyLoans()
    fun openLoanDetails(loan: Loan)
}