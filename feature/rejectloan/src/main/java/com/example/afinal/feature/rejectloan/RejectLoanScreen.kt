package com.example.afinal.feature.rejectloan

import com.example.afinal.feature.rejectloan.ui.RejectLoanFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getRejectLoanScreen() = FragmentScreen { RejectLoanFragment.newInstance() }