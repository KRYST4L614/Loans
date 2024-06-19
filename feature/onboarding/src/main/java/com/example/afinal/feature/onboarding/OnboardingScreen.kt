package com.example.afinal.feature.onboarding

import com.example.afinal.feature.onboarding.ui.OnboardingFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getOnboardingScreen() = FragmentScreen { OnboardingFragment.newInstance() }