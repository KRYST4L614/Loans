package com.example.afinal.navigation

import com.example.afinal.feature.home.HomeRouter
import com.example.afinal.feature.onboarding.getOnboardingScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class HomeRouterImpl @Inject constructor(
    private val router: Router
) : HomeRouter {
    override fun openOnboarding() = router.navigateTo(getOnboardingScreen())
}