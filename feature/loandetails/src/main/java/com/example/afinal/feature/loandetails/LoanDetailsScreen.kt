package com.example.afinal.feature.loandetails

import com.example.afinal.feature.loandetails.ui.LoanDetailsFragment
import com.example.afinal.shared.loans.domain.entities.Loan
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getLoanDetailsScreen(loan: Loan) = FragmentScreen { LoanDetailsFragment.newInstance(loan) }