package com.example.afinal.feature.acceptloan

import com.example.afinal.feature.acceptloan.ui.AcceptLoanFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import java.util.Date

fun getAcceptLoanScreen(sum: Int, issueDate: Date) =
    FragmentScreen { AcceptLoanFragment.newInstance(sum, issueDate) }