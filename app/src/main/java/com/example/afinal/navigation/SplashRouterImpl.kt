package com.example.afinal.navigation

import com.example.afinal.feature.auth.getAuthScreen
import com.example.afinal.feature.home.getHomeScreen
import com.example.afinal.feature.splash.SplashRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class SplashRouterImpl @Inject constructor(
    private val router: Router
) : SplashRouter {
    override fun openHome() = router.newRootScreen(getHomeScreen())

    override fun openAuth() = router.newRootScreen(getAuthScreen())

}