package com.example.afinal.feature.acceptloan

import com.example.afinal.feature.acceptloan.ui.AcceptLoanFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getAcceptLoanScreen(sum: Int) = FragmentScreen { AcceptLoanFragment.newInstance(sum) }