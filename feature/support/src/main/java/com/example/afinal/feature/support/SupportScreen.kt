package com.example.afinal.feature.support

import com.example.afinal.feature.support.ui.SupportFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getSupportScreen() = FragmentScreen { SupportFragment.newInstance() }