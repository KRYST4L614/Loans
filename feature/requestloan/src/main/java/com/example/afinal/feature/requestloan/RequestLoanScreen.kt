package com.example.afinal.feature.requestloan

import com.example.afinal.feature.requestloan.ui.RequestLoanFragment
import com.example.afinal.shared.loans.domain.entities.LoanConditions
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getRequestLoanScreen(loanConditions: LoanConditions) =
    FragmentScreen { RequestLoanFragment.newInstance(loanConditions) }