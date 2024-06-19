package com.example.afinal.feature.special

import com.example.afinal.feature.special.ui.SpecialFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getSpecialScreen() = FragmentScreen { SpecialFragment.newInstance() }