package com.example.afinal.navigation

import com.example.afinal.feature.auth.AuthRouter
import com.example.afinal.onboarding.OnboardingFragment
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class AuthRouterImpl @Inject constructor(private val router: Router) :
    AuthRouter {
    override fun openOnboarding() {
        router.navigateTo(FragmentScreen { OnboardingFragment.newInstance() })
    }
}