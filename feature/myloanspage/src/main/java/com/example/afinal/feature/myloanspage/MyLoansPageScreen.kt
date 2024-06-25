package com.example.afinal.feature.myloanspage

import com.example.afinal.feature.myloanspage.ui.MyLoansPageFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getMyLoans() = FragmentScreen{ MyLoansPageFragment.newInstance() }