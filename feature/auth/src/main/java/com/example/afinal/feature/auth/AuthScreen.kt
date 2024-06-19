package com.example.afinal.feature.auth

import com.example.afinal.feature.auth.ui.AuthFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getAuthScreen() = FragmentScreen { AuthFragment.newInstance() }