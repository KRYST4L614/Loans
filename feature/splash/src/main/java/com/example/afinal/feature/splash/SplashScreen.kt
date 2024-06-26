package com.example.afinal.feature.splash

import com.example.afinal.feature.splash.ui.SplashFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getSplashScreen() = FragmentScreen { SplashFragment.newInstance() }