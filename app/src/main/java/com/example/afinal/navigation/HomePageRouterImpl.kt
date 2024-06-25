package com.example.afinal.navigation

import com.example.afinal.di.LocalRouter
import com.example.afinal.feature.auth.getAuthScreen
import com.example.afinal.feature.homepage.HomePageRouter
import com.example.afinal.feature.myloanspage.getMyLoans
import com.example.afinal.feature.onboarding.getOnboardingScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class HomePageRouterImpl @Inject constructor(
    private val router: Router,
    @LocalRouter private val localRouter: Router
) : HomePageRouter {
    override fun openAuth() = router.replaceScreen(getAuthScreen())
    override fun openOnboarding() = router.navigateTo(getOnboardingScreen())
    override fun openMyLoans() = localRouter.navigateTo(getMyLoans())

}