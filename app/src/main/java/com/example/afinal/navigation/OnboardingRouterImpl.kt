package com.example.afinal.navigation

import com.example.afinal.feature.home.getHomeScreen
import com.example.afinal.feature.onboarding.OnboardingRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class OnboardingRouterImpl @Inject constructor(private val router: Router) : OnboardingRouter {
    override fun openHome() = router.replaceScreen(getHomeScreen())
}